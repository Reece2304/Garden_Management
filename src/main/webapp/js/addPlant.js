
//global elements within the webpage

var popup = document.getElementById("popup");
var error = document.getElementById("error");
var help = document.getElementById("help");
var close = document.getElementsByClassName("close")[0];
var closeError = document.getElementsByClassName("close")[1];
var Searchclose = document.getElementsByClassName("closeSearch")[0];
var displayClose = document.getElementsByClassName("closeDisplay")[0];
var editClose = document.getElementsByClassName("closeDisplay")[1];
var search = document.getElementsByClassName("search_container")[0];
var searchBar = document.getElementById("searchbar");
var CreateLog = document.getElementById("createLog");
var show = document.getElementsByClassName("plant_container")[0];
var edit = document.getElementsByClassName("plant_container")[1];
var addTo = "nothing";
var removePlants = document.getElementById("plants");
var currentSearch = "";
var currentPage = 1;
var dataArray = 0;
var back = document.getElementById("back");
var next = document.getElementById("next");
var _0x6518=["\x44\x48\x6B\x7A\x42\x30\x5F\x42\x54\x47\x36\x79\x66\x6D\x46\x6A\x33\x5F\x58\x49\x50\x78\x35\x33\x5F\x72\x69\x5A\x46\x4F\x56\x65\x55\x55\x64\x76\x6A\x44\x74\x55\x51\x65\x51"];_0x6518[0]
let file = document.getElementById("imageUpload");
var DisplayImageUpload = document.getElementById("DisplayImageUpload");
var identifyButton = document.getElementById("identifyButton");
var percent = document.getElementById("Percent");
var currentPlant = 0;
var g_role = "Free";


help.onclick = function(){popup.style.display="block"} //when help is clicked display the help information

close.onclick = function(){popup.style.display="none";} //when the close button is clicked hide the help information.

closeError.onclick = function(){error.style.display="none";} //when the close button is clicked hide the error information.


function closeEdit(){edit.style.display="none"; }

Searchclose.onclick = function(){search.style.display="none"; searchBar.value='';} //when the close button is clicked hide the search menu and reset the search bar

function closeDisplay()
{
	show.style.display="none";
	search.style.display="block";
	back.style.display = "";
	next.style.display = "";
}//when the close button is clicked hide the display information.


function Changeurl(value, pathvariable, imageurl)
{
	var array=[pathvariable.split(",")];
	array[0][array[0].length-1] = value;
	document.getElementById("url").href = "AddPlant/" + array + "/" + imageurl;
}

function ChangeEditurl(water, id, border, previous)
{
	document.getElementById("url").href = "EditPlant/" + water + "/" + id + "/" + border + "/" + previous;
	document.getElementById("url").style.display="block";
}

function ShowLog(image)
{
	CreateLog.style.display="block";
	edit.style.display="none";
	document.getElementById("form").action="/u/log/CreateLog/" + currentPlant;
}

window.onclick = function(event){ //if a user clicks outside the window then stop displaying the help information
	if(event.target == popup)
		{
			popup.style.display = "none";
		}
	if(event.target == error)
		{
			error.style.display = "none";
		}
	}
	
searchBar.addEventListener('keyup', (type) => { //event listener that constanlty checks for a user typing in the search bar
	const searchString = type.target.value;
	
	if(searchString.length >= 1) //if there's something in the search bar then
	{
		currentPage = 1;
		dataArray = 0;
		api.fetchPlants(searchString, dataArray); //pass the search string and the current array value to another function.
	}
	else
	{
		removePlants.style.display = "none"; //if the user removes the text in the searchbar then hide the information.
		back.style.display = "none";
		next.style.display = "none";
	}
});
	
	
function addPlant(role, currentPlants, clicked_id) //when a user clicks on the border '+' button
{
	if(currentPlants == 2 && role == "Free") //if the user is a free user then they can only add two plants
	{
		error.style.display="block";
	}
	else
	{
		var border = document.getElementById(clicked_id);
		addTo = clicked_id;
		search.style.display="block";
		removePlants.style.display = "none"; //display the search bar but hide the other elements
		back.style.display = "none";
		next.style.display = "none";
		DisplayImageUpload.style.display="none";
		identifyButton.style.display="none";
		percent.style.display = "none";
	}

}


function identify()
{
	const image = file.files[0];
	if(image.type == "image/jpeg" || image.type == "image/png")
	{
		DisplayImageUpload.src= URL.createObjectURL(event.target.files[0]);
		DisplayImageUpload.style.display="flex";
		removePlants.style.display = "none";
		back.style.display = "none"; 
		next.style.display = "none";
		identifyButton.style.display="";
		percent.style.display = "none";
	}
}

let api = {
	apiKey : "SOsTXRjFRJ7aSiBvLi0y",
	fetchPlants: function(searchString, dataIndex)
	{
		currentSearch = searchString; //update the value of the current search value.
		
		if(dataIndex < 0 && currentPage <= 1) //if it's not possible to go back a page
		{
			dataArray = 0;
		}
		if(dataIndex == 20) //if the user clicks the next button and 20 elements have already been displayed
		{
			currentPage++; //move onto the next page
			dataArray = 0;
		}
		if(dataIndex < 0 && currentPage > 1) //if it's possible to go back a page
		{
			currentPage--; //go back a page and display the last 5 elements of the previous page
			dataArray = 15;
		}
	    var request = "https://cryptic-plains-01436.herokuapp.com/https://api.floracodex.com/api/v1/plants/search?token=" 
	    + this.apiKey + "&page=" + currentPage + "&q=" + searchString; //api request with updated variables
		fetch(request,{
		method: 'GET',
		})
		.then((response) => response.json())
		.then((data) => this.displayPlants(data, dataArray));
	},
	
	changePage: function(i) //function called when the 'next' or 'back' button is clicked
	{
		if(i == 1) //if the next button is clicked
		{
		    dataArray +=5;
			this.fetchPlants(currentSearch, dataArray) //call the api request with the updated array value (next 5 elements or next page)
		}
		if(i == -1) //if the back button is clicked
		{
			dataArray -= 5;
			this.fetchPlants(currentSearch, dataArray) //call the api request with the updated array value (previous 5 elements or previous page)
		}
	},
		
	showPlant: function(add,id, border, water, PlantId, role) //when a user clicks on a plant then display that plant in more detail
	{
		g_role= role;
		currentPlant = PlantId;
		if(border!= null)
		{
			addTo = border;
		}
		try
		{
			var request = "https://cryptic-plains-01436.herokuapp.com/https://api.floracodex.com/api/v1/plants/" + id + "?token=" + this.apiKey;
			fetch(request,{
			method: 'GET',
			})
			.then(function(response) {
				if(!response.ok){api.displayPlant(add, id, null, water)}
			})
			.then((response) => response.json())
			.then((data) => this.displayPlant(add, id, data, water));
		}
		catch(error)
		{
			console.log("error");
			console.error(error.message)
		}
	},
	
	displayPlant: function(add, id, data, water)
	{
		var image = "https://www.escapeauthority.com/wp-content/uploads/2116/11/No-image-found.jpg";
		if(data != null)
		{
			var name = data.data.common_name;
			var image = data.data.image_url;
			var family = data.data.family_common_name;
			if(family == null)
			{
				family = data.data.family.common_name;
			}
					var scientific = data.data.scientific_name;

			if(name.includes("'"))
			{
				name = name.replace("'", "");
			}
					if(name == null)
		{
			name = "null";
		}

		if(image == null)
		{
			if(data.data.species[0].image_url == null )
			{
				image = "https://www.escapeauthority.com/wp-content/uploads/2116/11/No-image-found.jpg";
			}
			else
			{
				image = data.data.species[0].image_url;
			}
		}
		}

		if (name == null)
		{
			var pathvariable = [id , "null" , addTo, "Daily"];
		}
		else
		{
			var pathvariable = [id , name , addTo, "Daily"];
		}
		var imageURL = image.substring(8, image.length);
		if(add == false) //check if the user wants to edit the plant or not
		{
			
			edit.style.display="block";
			search.style.display="none";
			back.style.display = "none"; 
			next.style.display = "none";
			if(g_role =="Premium")
			{
						edit.innerHTML=`		<div id="edit">
		      	<span class="closeDisplay" onclick="closeEdit()">&times;</span>
		  		<div class="soloplantimg">
    				<img src="${image}">
    			</div>
    			<div class= "details">
    				<label> Plant name: ${name}</label>
    				<label> Family: ${family}</label>
    				<label> Scientific name: ${scientific}</label>
    				<div class="watering">
    				<label for="frequency">Watering Frequency:</label>
    				<select name="frequency" id ="frequency" style="font-size:larger;" onchange="ChangeEditurl(this.value, ${id}, '${addTo}', '${water}')">
    				<option value="Daily">Daily</option>
    				<option value="Weekly">Weekly</option>
    				<option value="Monthly">Monthly</option>
    				</select>
    				</div>
    			</div>
    					<a class="add" id="log" onclick='ShowLog()'> Create Log </a>
	    			<a class="add" id="url" style="display:none;">Save</a>
	    			<a class="add" id="remove" href="RemovePlant/${id}/${addTo}/${water}">Remove</a>
	    				</div>`
	    	var Logimage = document.getElementById('logimg').src= image;
			}
			else
			{
							edit.innerHTML=`		<div id="edit">
		      	<span class="closeDisplay" onclick="closeEdit()">&times;</span>
		  		<div class="soloplantimg">
    				<img src="${image}">
    			</div>
    			<div class= "details">
    				<label> Plant name: ${name}</label>
    				<label> Family: ${family}</label>
    				<label> Scientific name: ${scientific}</label>
    				<div class="watering">
    				<label for="frequency">Watering Frequency:</label>
    				<select name="frequency" id ="frequency" style="font-size:larger;" onchange="ChangeEditurl(this.value, ${id}, '${addTo}', '${water}')">
    				<option value="Daily">Daily</option>
    				<option value="Weekly">Weekly</option>
    				<option value="Monthly">Monthly</option>
    				</select>
    				</div>
    			</div>
	    			<a class="add" id="url" style="display:none;">Save</a>
	    			<a class="add" id="remove" href="RemovePlant/${id}/${addTo}/${water}">Remove</a>
	    				</div>`
			}

	    	var select = document.getElementById('frequency'); //make the default value the user's watering option
	    	for(var i, j=0; i = select.options[j]; j++)
	    	{
	    		if(i.value == water){
	    			select.selectedIndex = j;
	    			break;
	    		}
	    	}
		}
		else
		{
			show.style.display= "block";
			search.style.display="none";
			back.style.display = "none"; 
			next.style.display = "none";
			show.innerHTML = `		<div id="individual">
		      	<span class="closeDisplay" onclick="closeDisplay()">&times;</span>
		  		<div class="soloplantimg">
    				<img src="${image}">
    			</div>
    			<div class= "details">
    				<label> Plant name: ${name}</label>
    				<label> Family: ${family}</label>
    				<label> Scientific name: ${scientific}</label>
    				<div class="watering">
    				<label for="frequency">Watering Frequency:</label>
    				<select name="frequency" id ="frequency" style="font-size:larger;" onchange="Changeurl(this.value,'${pathvariable}', '${imageURL}' )">
    				<option value="Daily">Daily</option>
    				<option value="Weekly">Weekly</option>
    				<option value="Monthly">Monthly</option>
    				</select>
    				</div>
    			</div>
	    			<a class="add" id="url" href="AddPlant/${pathvariable}/${imageURL}">AddPlant</a>
	    				</div>`
		}

	},
	
	displayPlants: function(data, fivePlants){ // function that displays the return values from the api request
		console.log(data);
		back.style.display = ""; 
		next.style.display = "";
		DisplayImageUpload.style.display="none";
		identifyButton.style.display="none";
		percent.style.display = "none";
		var i;
		var name = "no name";
		var id = 123;
		var img = "https://www.escapeauthority.com/wp-content/uploads/2116/11/No-image-found.jpg";
		var family = "no family";
		var plantList = document.getElementsByClassName("plant");
		var y = 0;
		if(data.data.length < 5 ) //if there are less than 5 elements returned by the api search
		{
			back.style.display="none"; //don't allow the user to click the back and next button
			next.style.display="none";
			if(data.data.length == 0)
			{
				for(i = 0; i < 5 - data.data.length; i++) //for the remaining elements that don't have any values
				{
					plantList[y].style.display = "none"; //hide them from the list
					y++;
				}
				plantList[0].innerHTML=`<label style="text-align:center; font-size:30px;" onclick=""> No Results Found </label>`;
				plantList[0].id = ""; //update the id of the html element
				plantList[0].style.display="flex";
			}
			else
			{
				for(i = 0; i < data.data.length; i++) //for the number of elements returned by the api update the variables
				{
					id = id = data.data[i].id;
						if(data.data[i].family == null)
						{
							family = "unknown";
						}
						else 
						{
							family = data.data[i].family;
						}
			
						if(data.data[i].common_name == null)
						{
							if(data.data[i].family_common_name == null)
							{
								name = "no name";
							}
							else
							{
								name = data.data[i].family_common_name;
								name = name.substring(0, name.length - 7);
							}
					
						}
						else
						{
							name = data.data[i].common_name;
						}
						if(data.data[i].image_url == null)
						{
							img = "https://www.escapeauthority.com/wp-content/uploads/2116/11/No-image-found.jpg"; //if there's no image then display no image
						}
						else
						{
							img = data.data[i].image_url;
						}
						plantList[y].innerHTML = `
						<div class="plantimg">
    		 					<img src="${img}">
    					</div>
    					<div class= "details">
    					<label style="cursor:pointer;"> Plant name: ${name}</label>
    					<label style="cursor:pointer;"> Family: ${family}</label>
    					</div>`; //update the html of the plant
    					plantList[y].id = id; //update the id of the html element
					y++; //move onto the next element in the plant list
				}
				for(i = 0; i < 5 - data.data.length; i++) //for the remaining elements that don't have any values
				{
					plantList[y].style.display = "none"; //hide them from the list
					y++;
				}
			}
		}
		else
		{
			for(i = fivePlants; i < fivePlants+5; i++)
			{
				if(data.data.length == 0) //if the api doesn't return any values then display no resutlts found in all elements
				{
					plantList[y].innerHTML = `
					<div class="plantimg">
    		 			<img src="${img}">
    				</div>
    				<div class= "details">
    				<label> No Results Found</label>
    				<label> No Results Found</label>
    				</div>`;
				}
				else //otherwise update the elements with the api return values
				{
					id = data.data[i].id;
					if(data.data[i].family == null)
					{
						family = "unknown";
					}
					else 
					{
						family = data.data[i].family;
					}
			
					if(data.data[i].common_name == null)
					{
						if(data.data[i].family_common_name == null)
						{
							name = "no name"
						}
						else
						{
							name = data.data[i].family_common_name;
							name = name.substring(0, name.length - 7);
						}
					
					}
					else
					{
						name = data.data[i].common_name;
					}
					if(data.data[i].image_url == null)
					{
						img = "https://www.escapeauthority.com/wp-content/uploads/2116/11/No-image-found.jpg";
					}
					else
					{
						img = data.data[i].image_url;
					}
					plantList[y].innerHTML = `
					<div class="plantimg">
    		 				<img src="${img}">
    				</div>
    				<div class= "details">
    				<label style="cursor:pointer;"> Plant name: ${name}</label>
    				<label style="cursor:pointer;"> Family: ${family}</label>
    				</div>`; //update the html
    				plantList[y].id = id;
    				plantList[y].style.display = "flex";
    				y++;
			}		
		}
			
			
		}

		removePlants.style.display = "block"; //display the html element, 'back' and 'next' buttons

	}

};

function identifyImage() {
		percent.style.display = "flex";//code from w3schools: https://www.w3schools.com/howto/tryit.asp?filename=tryhow_js_progressbar_label_js
		var i = 0;
  		if (i == 0)
  		{
    		i = 1;
    		var width = 10;
    		var id = setInterval(frame, 10);
    		var elem = document.getElementById("Bar");
    		function frame()
    		{
      			if (width >= 100)
      			{
        			clearInterval(id);
        			i = 0;
      			}
      			else
      			{
        			width++;
        			elem.style.width = width + "%";
        			elem.innerHTML = width  + "%";
      			}
    		}
  		}
  		
  		//code from plant.id (https://github.com/Plant-id/Plant-id-API/blob/master/javascript/sync_identification_example.html)
    const files = [...document.querySelector('input[type=file]').files];
    const promises = files.map((file) => {
      return new Promise((resolve, reject) => {
          const reader = new FileReader();
          reader.onload = (event) => {
            const res = event.target.result;
            console.log(res);
            resolve(res);
          }
          reader.readAsDataURL(file)
      })
    })
    
    Promise.all(promises).then((base64files) => {
      console.log(base64files)
            
      const data = {
        api_key: "Bl0Ico24KuCaNFcQh99X4Xqzidzc6eDFE1nW451pf09Zdicxtw",
        images: base64files,
        modifiers: ["crops_fast", "similar_images"],
        plant_language: "en",
        plant_details: ["common_names",
                          "url",
                          "name_authority",
                          "wiki_description",
                          "taxonomy",
                          "synonyms"]
      };
      
      fetch('https://api.plant.id/v2/identify', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      })
      .then(response => response.json())
      .then(data => {
        api.fetchPlants(data.suggestions[0].plant_name, 0);
        searchBar.value=data.suggestions[0].plant_name;
      })
      .catch((error) => {
        console.error('Error:', error);
      });
    })
  
};




