let weather = {
	"apiKey": "c83f663dd9abec625bd1e1cb99a5fea7",
	fetchWeather: function(lat, lon) {
		var request = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + this.apiKey;
		fetch(request)
		.then((response) => response.json())
		.then((data) => this.displayWeather(data, lat, lon));
	},
	
	displayWeather: function(data, lat, lon){
		const { name } = data;
		const { icon, description } = data.weather[0];
		const { temp, humidity } = data.main;
		const { speed } = data.wind;
		var currentTime = new Date();
		var minutes = "0" + currentTime.getMinutes();
		if(minutes.length == 2)
		{
			document.querySelector(".city").innerText = "Weather in " + name + " - " + currentTime.getHours() + ":" + minutes;
		}
		else
		{
			document.querySelector(".city").innerText = "Weather in " + name + " - " + currentTime.getHours() + ":" + currentTime.getMinutes();
		}
		
		document.querySelector(".image").src = "https://openweathermap.org/img/wn/"+ icon +"@2x.png";
		document.querySelector(".description").innerText = description;
		document.querySelector(".temp").innerText = Math.ceil(temp) + String.fromCharCode(176) +  "C";
		document.querySelector(".humidity").innerText = "Humidity: " + humidity + "%";
		document.querySelector(".wind").innerText = "Wind Speed: " + Math.round((speed * 2.237)) + " mph";
				var request = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=minutely,daily,alerts&units=metric&appid=" + this.apiKey;
		fetch(request)
		.then((response) => response.json())
		.then((data) => this.displayHourly(data));
	},
	
	displayHourly: function(data){ //function for the hourly data
	
		var i;
		var y = 0;
		var display = document.getElementsByClassName("hourly");
		
		//for loop that displays the time (current +2hrs, +3hrs, +4hrs)
		for(i=0; i < 3; i++)
		{
			document.getElementById(y).style.marginTop = "10px"; // change the margin on the hourly elements
			document.getElementById("desc" + y).style.fontWeight = "bold"; 
			document.getElementById("desc" + y).style.marginTop = "5px"; // change the margin on the description elements
			const { name } = data;
			const { icon, description } = data.hourly[i+2].weather[0]; //find the weather from within the JSON response
			const { temp, humidity } = data.hourly[i+2];
			const { speed } = data.current.wind_speed;
			document.querySelector(".hourlyimage" + y).src = "https://openweathermap.org/img/wn/"+ icon +"@2x.png"; //update the divs with the values returned from the API request
			document.querySelector(".hourlyDesc" + y).innerText = description;
			document.querySelector(".hourlytemp" + y).innerText = Math.ceil(temp) + String.fromCharCode(176) +  "C";
			var date = new Date(data.hourly[i+2].dt * 1000);
			var hours = date.getHours();
			
			//convert the time to 12 hours
						
			if(hours >= 12 && hours <= 23)
			{
				document.querySelector(".time" + y).innerText = (hours % 12) + " pm";
			}
			else
			{
				if(hours == 0)
				{
					document.querySelector(".time" + y).innerText = "12 am";
				}
				else
				{
					document.querySelector(".time" + y).innerText = hours + " am";
				}
			
			}
			y++;	//move to the next div element	
		}
	}

};

let geocode = {
	reverseGeocode: function(latitude, longitude)
	{
		var apikey = '5e4520d0feec477aa604f800d9ce556b';
  		var api_url = 'https://api.opencagedata.com/geocode/v1/json'
  		var request_url = api_url
    	+ '?'
    	+ 'key=' + apikey
    	+ '&q=' + encodeURIComponent(latitude + ',' + longitude)
    	+ '&pretty=1'
    	+ '&no_annotations=1';

  		var request = new XMLHttpRequest();
  		request.open('GET', request_url, true);

  		request.onload = function() {
    		if (request.status === 200){ //success response
            	var data = JSON.parse(request.responseText);
      			weather.fetchWeather(latitude, longitude);//output the weather at the user's location

    		} else if (request.status <= 500){ 
      			//error response
				console.log("unable to get location! Response code: " + request.status);
      			var data = JSON.parse(request.responseText);
      			console.log('error msg: ' + data.status.message);
    		} else {
      		console.log("server error");
    		}
  		};

 		request.onerror = function() {	//connection error
    		console.log("unable to connect to server");        
  		};

  		request.send();  // make the request
	},
	getLocation: function() {
		function success(data)
		{
			geocode.reverseGeocode(data.coords.latitude, data.coords.longitude);
		}
		if(navigator.geolocation)
		{
			navigator.geolocation.getCurrentPosition(success, console.error);
	    }
	    else{
	    	weather.fetchWeather("London");
	    }
	}
};


geocode.getLocation();