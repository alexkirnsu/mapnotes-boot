<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html >
<html lang="en">
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>MapNotes</title>
    <style>
      #map {
        height: 100%;
      }
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
  </head>
  <body>
    <div id="form" style="display: none;">
        <form action="/notes" method="post">
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        <h2 class="form-signin-heading" style = "text-align: center;">Make note</h2>
            <table>
                <tr><td></td> <td><input type="text" class="form-control" style="display: none;" name="lat" id = "lat"  required> </td> </tr>
                <tr><td></td> <td><input type="text" class="form-control" style="display: none;" name="lng" id ="lng" required> </td> </tr>

                <tr>
                    <td>
                        Place:
                    </td>
                    <td>
                        <select class="form-control" name="place">
                          <option selected value="Meal">Meal</option>
                          <option value="Rest">Rest</option>
                          <option value="Market">Market</option>
                          <option value="Other">Other</option>
                        </select>
                    </td>
                </tr>
                <tr><td>Comment: </td> <td><textarea name="message" maxlength="255" class="form-control" rows="5" cols="18" required></textarea> </td> </tr>
                <tr><td>Private note: </td> <td><input type="checkbox" name="privacy" value="private" class="form-control"></input> </td> </tr>
                <tr><td/><td><button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button></td></tr>
            </table>
        </form>
    </div>

    <div id = "noteInfo" style="display: none;">
    <h2 class="form-signin-heading" style = "text-align: center;">Note Info</h2>
        <div>
            <div>
                <div>
                    <div>
                        <div style="display: flex; flex-direction: column;">
                            <div>
                                <div style="display: flex; flex-direction: row; align-items: center">
                                    <div style="margin-left: 10px">
                                        <div style="width: 35px">Place: </div>
                                        <textarea
                                            id = "place_curr"
                                            name="place_curr"
                                            class="form-control"
                                            rows="1"
                                            cols="14"
                                            readonly
                                        >
                                        </textarea>
                                    </div>
                                </div>

                                <form:form action="" method="DELETE" id = "noteInfoFormDelete" style="display: none;">
                                    <div style="display: flex; align-self: flex-end; justify-content: flex-end;">
                                        <div style="margin-top: 10px">
                                            <button id="deleteNoteBtn" class="btn btn-lg btn-primary btn-block" type="submit">Delete</button>
                                        </div>
                                    </div>
                                </form:form>
                            </div>

                            <div style="margin-left: 10px">
                                <div>
                                    <div style="width: 35px; margin-top: 10px">Comments: </div>
                                    <div id="commentsTable" style="margin-bottom: 10px"></div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <form id="logout" action="/logout" method="POST" style="display: none;">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input id="logoutButton" type="submit" value="logout">
    </form>
    <%--<a id = "logout" href="/logout"></a>--%>
    <div id="map" height="460px" width="100%"></div>
    <script type="text/javascript">
      let notes = '${notes}';
      let notesJSON = JSON.parse(notes);
      let map;
      let infowindow;
      let novosibirsk = {lat: 55.0121, lng: 82.9408};
      let availableToDelete =  '${availableToDelete}';

      let markers = [];

      function CenterControl(controlDiv, map) {
      	  let mealNotes = extractNotes('Meal');
      	  let restNotes = extractNotes('Rest');
      	  let marketNotes = extractNotes('Market');
      	  let otherNotes = extractNotes('Other');

          let controlMeal = document.createElement('div');
          let controlRest = document.createElement('div');
          let controlMarket = document.createElement('div');
          let controlOther = document.createElement('div');
          let controlLogout = document.createElement('div');

      	  initControlDiv(controlMeal, '#0E78CD', 'Meal');
          initControlDiv(controlRest, '#D7513C', 'Rest');
          initControlDiv(controlMarket, '#4DD73C', 'Market');
          initControlDiv(controlOther, 'yellow', 'Other');
          initControlDiv(controlLogout, '#fff', 'Logout');

      	  controlDiv.appendChild(controlMeal);
          controlDiv.appendChild(controlRest);
          controlDiv.appendChild(controlMarket);
          controlDiv.appendChild(controlOther);
          controlDiv.appendChild(controlLogout);

          controlLogout.addEventListener('click', function() {
              document.getElementById('logoutButton').click();
          });
       }


       function initControlDiv(controlText, backgroundColor, text) {
       	  controlText.style.backgroundColor = backgroundColor;
          controlText.style.border = '2px solid #fff';
          controlText.style.borderRadius = '3px';
          controlText.style.boxShadow = '0 2px 6px rgba(0,0,0,.3)';
          controlText.style.cursor = 'pointer';
          controlText.style.marginBottom = '22px';
          controlText.style.textAlign = 'center';
       	  controlText.style.color = 'rgb(25,25,25)';
          controlText.style.fontFamily = 'Roboto,Arial,sans-serif';
          controlText.style.fontSize = '16px';
          controlText.style.lineHeight = '38px';
          controlText.style.paddingLeft = '5px';
          controlText.style.paddingRight = '5px';
          controlText.innerHTML = text;
       }

       function extractNotes(optionType) {
       	let notesByType = [];
       	notesJSON.forEach(function(note) {
       		if (note.place == optionType) {
       			notesByType.push(note);
       		}
       	});
       	return notesByType;
       }

      function initMap() {


        let content = document.getElementById('form');
        let isEditMode = false;
        let marker;

        map = new google.maps.Map(document.getElementById('map'), {
          center: novosibirsk,
          zoom: 13
        });

        var centerControlDiv = document.createElement('div');
        var centerControl = new CenterControl(centerControlDiv, map);

        centerControlDiv.index = 1;
        map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

        loadAllMarkers(map);

        infowindow = new google.maps.InfoWindow({
          content: content
        });


        google.maps.event.addListener(map, 'click', function(event) {
        	if (!isEditMode) {
        		console.log('Edit mode started');

    			marker = getMarkerByEvent(event, map);

		        document.getElementById('form').style.display = "block";
		        infowindow.open(map, marker);

		        setCoordinateToForm(event);
		        isEditMode = true;
        	} else {
        		console.log('Change marker place');

				    marker.setMap(null);
            marker = null;
       			marker= getMarkerByEvent(event, map);
       			infowindow.open(map, marker);
            	setCoordinateToForm(event);

        	}
         });

        google.maps.event.addListener(infowindow, "closeclick", infoWindowCloseListener = function () {
    		console.log('Stop edit mode. Close info windows');

      		isEditMode = false;
      		marker.setMap(null);
   			marker = null;
			
			infowindow= new google.maps.InfoWindow({
    			content: content
    		});
    		infowindow.open(map);

    		google.maps.event.addListener(infowindow, "closeclick", infoWindowCloseListener);
    		
		});
      }

      function getMarkerByEvent(event, map) {
      	return new google.maps.Marker({
            position: event.latLng,
            map: map
      	});
      }

      function loadAllMarkers(map) {
        let blueMarkerIcon = 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png';
        let redMarkerIcon = 'http://maps.google.com/mapfiles/ms/icons/red-dot.png';
        let greenMarkerIcon = 'http://maps.google.com/mapfiles/ms/icons/green-dot.png';
        let yellowMarkerIcon = 'http://maps.google.com/mapfiles/ms/icons/yellow-dot.png';
      	let markerInfoMap = new Map();
      	let content = document.getElementById('noteInfo');
      	let infonote = new google.maps.InfoWindow({
        	content: content
        });

      	notesJSON.forEach(function(note) {
      		let currMarker = new google.maps.Marker({ 
      			position: {
      				lat: note.lat, 
      				lng: note.lng
      			},
                map: map
            });
            
            let info = {
            	id: note.id,
            	place: note.place, 
            	message: note.message,
                alien: note.alien,
                commentNumber: note.commentNumber
            };

            switch(note.place) {
              case 'Meal':
                currMarker.setIcon(blueMarkerIcon);
                break;
              case 'Rest':
                currMarker.setIcon(redMarkerIcon);
                break;
              case 'Market':
                currMarker.setIcon(greenMarkerIcon);
                break;
              case 'Other':
                currMarker.setIcon(yellowMarkerIcon);
                break;
            }

      		markers.push(currMarker);
      		markerInfoMap.set(currMarker, info);
      	});

		markers.forEach(function(marker){
  			marker.addListener('click', function() {
  				let info = markerInfoMap.get(marker);
    			document.getElementById('place_curr').value = info.place;
                document.getElementById("noteInfoFormDelete").action = "/notes/" + info.id;
                if (info.alien || info.commentNumber > 1) {
                    document.getElementById("noteInfoFormDelete").style.display = 'none';
                } else {
                    document.getElementById("noteInfoFormDelete").style.display = 'block';
                }
            	document.getElementById('noteInfo').style.display = "block";
            	getComments(info.id);
            	infonote.open(map, marker);

            	google.maps.event.addListener(infonote, "closeclick", function () {
    				infonote= new google.maps.InfoWindow({
        				content: content
        			});
        			infonote.open(map);
				});
  			});
		});
      }

      function setCoordinateToForm(event) {
        document.getElementById('lat').value = event.latLng.lat();
        document.getElementById('lng').value = event.latLng.lng();
      }

    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAGhx8Oe_0Pc3JwDYoi4vBXtH-EzbMkOMs&callback=initMap">
    </script>

    <script src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
    <script type="text/javascript">
        function getComments(noteId) {
            console.log('logGetComments');

            $.ajax({
                type: "GET",
                url: "/notes/" + noteId + "/comments",
                success: function(response) {
                    $("#commentsTable").html( response );
                },
                error: function () {
                    alert('fail');
                }
            });
        }
    </script>
  </body>
</html>