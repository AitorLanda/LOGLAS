<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script>

	var jsonString = '${userStats}';
	var userStats = JSON.parse(jsonString);
	console.log(userStats);
	
	// Format data into usable columns for c3
	var gameTypeCountObj = userStats["gameTypeCount"];
	var columnsCountDonut = Object.keys(gameTypeCountObj).map(function(gameKey) {
		 return [gameKey, gameTypeCountObj[gameKey]];
	});
	
	var gameTypeWinCountObj = userStats["gameTypeWinCount"];
	var dataWinCountGauges = Object.keys(gameTypeWinCountObj).map(function(gameKey) {
		 return [gameKey, gameTypeWinCountObj[gameKey]];
	});
	
	var gameTypeTotalScoreCountObj = userStats["gameTypeTotalScoreCount"];
	var dataTotalScoreCountBar = Object.keys(gameTypeTotalScoreCountObj).map(function(gameKey) {
		 return [gameKey, gameTypeTotalScoreCountObj[gameKey]];
	});
	var categoriesList = [];
	var totalScoreData = [];
	totalScoreData.push("Total Score");
	for (var item in dataTotalScoreCountBar) {
		categoriesList.push(dataTotalScoreCountBar[item][0]);
		totalScoreData.push(dataTotalScoreCountBar[item][1]);
	}

	
	// Start
	window.onload = init;
	
	/**
	 * @brief	Init
	 **/
	function init(event) {
		// Check if there is any stat
		if (isEmptyObj(userStats)) {
			$("#info").text("No stats available");
		} else {
			$("#info").empty();
			drawTotalAverageGauge();
			drawPlayedGameTypeCountDonut();
			drawWinLoseGameTypeGauges();
			drawTotalScoreGameTypeCountBar();
		}
	} 
	
	/**
	 * @brief	Check if object is empty
	 * @param	{object}
	 * @return	{boolean}
	 **/
	function isEmptyObj(object) { 
        for (var key in object) { 
            if (object.hasOwnProperty(key)) { 
                return false; 
            } 
        }
        return true; 
    }
	
	/**
	 * @brief	Draws a gauge using
	 *			Average total points gained
	 *			Max gauge limit: one-time maximun points gained
	 * 			Min gauge limit: one-time minimum points gained
	 **/
	function drawTotalAverageGauge() {

		var lineStatusChart = c3.generate({
		    bindto: '#totalAverageGauge',
		    data: {
		        columns: [
		            ['Total average score', userStats.averageScore]
		        ],
		        type: 'gauge'
		    },
			
			gauge: {
				label: {
					format: d3.format('.1f'),
					units: "Units"
				},
				min: userStats.min,
				max: userStats.max
			},
			
			size: {
		        height: 150
		    },
			
			color: {
		        pattern: ['#c9b6e4']
		    },
		    
		    tooltip: {
		        show: false
		    }
		});
		
	}
	
	/**
	 * @brief	Draws a donut using columnsCountDonut data
	 *			
	 **/
	function drawPlayedGameTypeCountDonut() {
		
		var chart = c3.generate({
			bindto: '#playedGameTypeCountDonut',
		    data: {
		        columns: columnsCountDonut,
		        type : 'donut'
		    },
		    
		    donut: {
		        title: "Played Game Types"
		    },
		    
		    color: {
		        pattern: ['#c9b6e4', '#4ecda4']
		    },
		    
		    tooltip: {
		    	format: {
		    		value:
		    			function (value, ratio, id) {
		                    var format = d3.format('');
		                    return format(value);
		    		}
		    	}
		    },
		    
		    legend: {
		        show: false
		    }
		    
		});
		
	}
	
	/**
	 * @brief	Draws dynamic gauges
	 *			For all game types available
	 *			Shows win counts / total plays
	 *			
	 **/
	function drawWinLoseGameTypeGauges() {
		for (var i = 0; i < dataWinCountGauges.length; i++) {
			drawWinLoseGameTypeGauge(i);
		}
	}
	
	/**
	 * @brief	Draws a gauge using game type
	 *			
	 **/
	function drawWinLoseGameTypeGauge(index) {
		
		var generatedId = "winLostGameTypeGauge" + index;
		var div = document.createElement("div");
		div.setAttribute("id", generatedId);
		document.getElementById("winLostGameTypeGauge").appendChild(div);
		generatedId = "#" + generatedId;
		
		var lineStatusChart = c3.generate({
		    bindto: generatedId,
		    data: {
		        columns: [
		            [dataWinCountGauges[index][0], dataWinCountGauges[index][1]]
		        ],
		        type: 'gauge'
		    },
			
			gauge: {
				label: {
					units: "Units"
				},
				min: 0,
				max: columnsCountDonut[index][1]
			},
			
			color: {
		        pattern: ['#c9b6e4', '#4ecda4'],
		        threshold: {
		            unit: 'value',
		            max: columnsCountDonut[index][1],
		            value: columnsCountDonut[index][1] /2
		        }
		    },
			
		    size: {
		        height: 100
		    },
		    
		    tooltip: {
		    	format: {
		    		value:
		    			function (value, ratio, id) {
		                    var format = d3.format('');
		                    return format(value);
		    		}
		    	}
		    }
		});
		
	}
	
	/**
	 * @brief	Draws a bar chart for all points sum for each game type
	 *			
	 **/
	function drawTotalScoreGameTypeCountBar() {
		var chart = c3.generate({
			bindto: '#totalScoreGameTypeCountBar',
		    data: {
		        columns: [
		        	totalScoreData
		        ],
		        type: 'bar'
		    },
		    
		    bar: {
		        width: {
		            ratio: 0.5 // this makes bar width 50% of length between ticks
		        }
		    },
		    
		    axis: {
		    	x: {		            
		            type: 'category',
		            categories: categoriesList,
		            tick: {
		              	centered: true
		            }
		    	}
			},
			
			color: {
		        pattern: ['#c9b6e4', '#4ecda4']
		    }
		    
		});

	}
	
	
	
</script>

<div class="userContainer">
	
	<h1>
		 <spring:message code="label.userStats" />
	</h1>
	<div id="info"></div>
	
	<h5>
		<spring:message code="label.userAverage" />
	</h5>
	<div id="totalAverageGauge" style=" max-width: 920px; margin: 0px auto;"></div>
	<h5>
		<spring:message code="label.userPlayedGames" />
	</h5>
	<div id="playedGameTypeCountDonut" style="height: max-width: 920px; margin: 0px auto;"></div>
	<h5>
		<spring:message code="label.userWonPlays" />
	</h5>
	<div id="winLostGameTypeGauge">
		<!-- AUTO GENERATED FOR EACH GAME : WIN - LOSE RATIO -->
	</div>
	<h5>
		<spring:message code="label.userTotalScore" />
	</h5>
	<div id="totalScoreGameTypeCountBar" style="max-width: 920px; margin: 0px auto;"></div>
	
</div>
