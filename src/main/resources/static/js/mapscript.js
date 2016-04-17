		var map = L.map('map', {
			editable : true
		}).setView([ 52.472116, 16.879807 ], 13);

		L.tileLayer(
						'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}',
						{
							attribution : 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
							id : 'mapbox.streets',
							accessToken : 'pk.eyJ1IjoiYWJsYWJsYWJsYSIsImEiOiJFV2VRd0hBIn0.r9ZdoGzO2cBXxxcK7NISig'
						}).addTo(map);

		var OSM = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png');
		GOOGLE = L.tileLayer('http://{s}.google.com/vt/lyrs=s&amp;x={x}&amp;y={y}&amp;z={z}', {subdomains : [ 'mt0', 'mt1', 'mt2', 'mt3' ]});
		SATELITE = L.tileLayer('http://{s}.google.com/vt/lyrs=m&amp;x={x}&amp;y={y}&amp;z={z}', {
					subdomains : [ 'mt0', 'mt1', 'mt2', 'mt3' ]
				});

		var baseMaps = {
			"OpenStreetMap" : OSM,
			"Satelita" : GOOGLE,
			"GoogleMaps" : SATELITE
		};
		/*
		 map.on('click', function(e) {
		 alert(e.latlng);
		 });
		 */
		map.on('draw:created', function(e) {
			var type = e.layerType, layer = e.layer;

			map.addLayer(layer);

			if (type === 'marker') {

				layer.bindPopup('Szerokosc/Dlugosc: ' + layer.getLatLng())
						.openPopup();
			}

		});

		//poniżej zmienne warstwy - typeName to workspace:nazwa warstwy na geoserverze
		var defaultParameters = {
			service : 'WFS',
			version : '1.0.0',
			request : 'GetFeature',
			typeName : 'topp:punkty_wgs84',
			maxFeatures : 200,
			outputFormat : 'text/javascript',
			format_options : 'callback: getJson'
		};

		var owsrootUrl = "https://abc";

		//tutaj z powyższych tworzony jest właściwy URL
		var parameters = L.Util.extend(defaultParameters);
		var URL = owsrootUrl + L.Util.getParamString(parameters);

		var WFSLayer = null; //zmienna warstwy - na razie pusta
		var ajax = $
				.ajax({
					url : URL,
					dataType : 'jsonp', //trzeba pamiętać, że jsonp musi być w geoserverze dostępne - sprawdzić np. przez GetCapabilities
					jsonpCallback : 'getJson',
					success : function(response) {
						WFSLayer = L
								.geoJson(
										response,
										{
											onEachFeature : function(feature,
													layer) {
												popupOptions = {
													maxWidth : 200
												};
												//treść popupa w zmiennej - dostęp do atrybutów warstwy poprzez feature.properties.NAZWA ATRYBUTU 
												var zawartosc_popupa = "Atrybut"
														+ feature.properties.nazwa;
												layer.bindPopup(
														zawartosc_popupa,
														popupOptions);
											}
										}).addTo(map);
					}
				});

		///////////////////PRZYKŁAD 2 : DODAWANIE PUNKTÓW NA GEOSERVER

		//dodanie pustej warstwy "drawnItems" -zostawiam nazwę tak jak jest w przykładach innych
		var drawnItems = new L.FeatureGroup();
		var currentLayer;
		map.addLayer(drawnItems);

		//dodanie kontrolek rysujacych punkt - reszta jest wyłączona
		var drawControl = new L.Control.Draw({
			edit : {
				featureGroup : drawnItems
			},
			draw : {
				polygon : true,
				polyline : false,
				rectangle : true,
				circle : true
			}
		}).addTo(map);

		//następny element to obsługa eventu dodania punktu - jest uzupełniona warstwa drawnItems, dodana do mapy a nastepnie otworzony dialog
		map.on('draw:created', function(e) {
			var layer = e.layer;
			drawnItems.addLayer(layer);
			currentLayer = layer;
			map.addLayer(drawnItems);
			dialog.dialog("open");
			lng.value = layer.getLatLng().lng;
			lat.value = layer.getLatLng().lat;
		});

		// dialog obsługiwany za pomocą jquery uie
		// po kliknięciu "Dodaj punkt..." jest wywoływana funkcja setData() - jest określona niżej
		var dialog = $("#dialog").dialog({
			autoOpen : false,
			height : 200,
			width : 600,
			modal : true,
			position : {
				my : "center center",
				at : "center center",
				of : "#map"
			},
			buttons : {
				"Dodaj punkt do bazy" : setData,
				Anuluj : function() {
					dialog.dialog("close");
					//map.removeLayer(drawnItems);
				}
			},
			close : function() {
				form[0].reset();
				console.log("Okno dialogowe zostało zamknięte");
			}
		});

		var form = dialog.find("form");
		//.on("submit", function(event) {
		//    event.preventDefault();
		//});

		//najważniejsza funkcja w dodawaniu punktów - tworzy na podstawie dialogu i punktu leaflet draw WFS-T i wysyła do geoservear
		//będzie to działać przy założeniu, że na geoserverze podpięta jest wartstwa PostGIS, aktywny jest WFS i został rozwiązany 
		//problem Cross Domain - na moim serwerze o ten ostatni problem nie musicie się martwić.
		function setData() {
			//wartość zmiennej z dialogu przypisywana jest do zmiennej js
			var description = inputDescription.value;
			var pollutionType = type.value;
			var latitude = lat.value;
			var longitude = lng.value;
			var inputEmail = email2.value;

			drawnItems
					.eachLayer(function(layer) {
						//w zmiennej postData jest tworzony xml WFS-T
						var postData = '<wfs:Transaction\n'
                  + '  service="WFS"\n'
                  + '  version="1.1.0"\n'
                  + '  xmlns:topp="http://www.openplans.org/topp"\n'//"topp" to workspace na geoserverze - zmienić na własny
                  + '  xmlns:wfs="http://www.opengis.net/wfs"\n'
                  + '  xmlns:gml="http://www.opengis.net/gml"\n'
                  + '  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"\n'
                  + '  xsi:schemaLocation="http://www.opengis.net/wfs\n'
                  + '                      http://schemas.opengis.net/wfs/1.1.0/WFS-transaction.xsd">\n'
								+ '  <wfs:Insert>\n'
								+ '    <topp:punkty_wgs84>\n' //"punkty_wgs84" to nazwa warstwy na geoserverze, na geoserverze - zmienić na własną, musi być w WSG 84
								+ '      <topp:nazwa>'
								+ description
								+ '</topp:nazwa>\n' ///zmienna z dialogu wpisywana jest do atrybutu "nazwa" w warstwie postgis
								+ '      <topp:the_geom>\n'
								+ '        <gml:Point srsDimension="2" srsName="EPSG:4326">\n'
								+ '          <gml:pos>'
								+ layer.getLatLng().lng
								+ ' '
								+ layer.getLatLng().lat
								+ '</gml:pos>\n' //współrzędne nowego punkty służą jako pole geometrii w postgis
								+ '        </gml:Point>\n'
								+ '      </topp:the_geom>\n'
								+ '    </topp:punkty_wgs84>\n'
								+ '  </wfs:Insert>\n' + '</wfs:Transaction>';

						//za pomocą jquery postData wysyłane jest do geoservera

					});

			$.ajax({
				url : "http://data-collector-app.herokuapp.com/reports/add",
				type : "POST",
				dataType : "json",
				data : {
					lat : latitude,
					lng : longitude,
					type : pollutionType, //TYP ZGLOSZENIA/ZANIECZYSZCZENIA OD 0-5 
					description : description, //NIE WYMAGANE
					email : inputEmail
				//NIE WYMAGANE
				},
				success : function(response) {
					console.log("SUCCESS:" + response.content);
					if (response.success) {
						//$("body").append("Report added. Id of new report is:"+response.content);
					} else {
						console.log("FAILURE:" + response.error);
						alert(response.error);
					}
				},
				//Funkcja jest wywołana tylko w przypadku problemu z komunikacją z serwerem.
				error : function(response) {
					console.log("ERROR");
				}
			});

			dialog.dialog("close");
		};

		L.control.scale().addTo(map);
		L.control.layers(baseMaps).addTo(map);