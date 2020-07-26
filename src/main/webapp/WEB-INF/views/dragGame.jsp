<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>

<script type="text/javascript" charset="UTF-8">

	// Retrieve lobby ID that we just joined
	var lobbyId = "${lobby.getId()}";
	
	//Retrieve user ID
	var userId = "${user.getId()}";
	
	// Create web socket with lobbyId attached
    var webSocket = new WebSocket('ws://' + document.location.host + '/POPBL5/lobbychat/' + lobbyId);
	
	
    webSocket.onerror = function(event) {
      	onError(event)
    };

    webSocket.onopen = function(event) {
      	onOpen(event)
    };

    webSocket.onmessage = function(event) {
      	onMessage(event)
    };
    

	// parse variables to int
	lobbyId = parseInt(lobbyId);
	userId = parseInt(userId);
	// Set up a player score object for this unique player
	var scoreObj = {
		'action'	: "updateScore",
		'lobbyId'	: lobbyId,
		'userId'	: userId,
		'score'		: 0
    };
	// Will store the chosen answer after user click
	var chosenAnswer = "";
	// Will store the time offset needed for timeouts (in order to acumulate question times one after other)
	var offset = 0;
	
	
	var container;
	var lobbyGroup;
	var playerList;
    /**
      * Data retrieved by websocket
      * event.data contains the data that Endpoint broadcasteds
      */
    function onMessage(event) {
    	// Since we have a JSON format data, we have a known object structure.
    	container = JSON.parse(event.data);
    	
    	// Show lobby players (SCORE-BOARD)
    	lobbyGroup = searchLobbyGroup(lobbyId, container["lobbyGroupsList"]);
    	playerList = lobbyGroup["playersList"];
    	listPlayersScoreboard(playerList);
    	
    }
    
    /**
     * Search for this lobby group info
     * @param {array} items of lobbies containing info and players inside
     * @param {number} containing the id of the actual lobby
     */
    function searchLobbyGroup(lobbyId, lobbyGroupsList){
        for (var i=0; i < lobbyGroupsList.length; i++) {
            if (lobbyGroupsList[i]["lobbyId"] === parseInt(lobbyId, 10)) {
                return lobbyGroupsList[i];
            }
        }
    }
    
    /**
     * Shows (refreshes) scoreboard table
     * @param {array} items of players containing scores
     */
    function listPlayersScoreboard(playerList) {
    	// Remove old info
    	$("#scoreboard").empty();
    	
    	// Add head
    	var head = '<head><tr>';
    	head += '<th> Player </th>';
    	head += '<th> Score </th>';
    	$("#scoreboard").append(head + '</tr><thead>');
    	// Add boddy
    	var body = '<tbody>';
    	playerList.forEach(function(obj){ // For each row
    		//Each column
    		var row = '<tr> <td>' + obj["username"] + '</td>';
    		row += '<td>' + obj["score"] + '</td>';
    		body += row + '</tr>';
    	});
    	// Append generated rows/columns to table
    	$("#scoreboard").append(body+'</tbody>');
    }

    function onOpen(event) {    	
    }

    function onError(event) {
      	alert("Could not connect to server");
    }

    // This function is a trigger (answer selection) --> update score (after timeout is carried out)
    function send(jsonObj) {
      	// Send thought websocket
      	webSocket.send(jsonObj);
      	return false;
    }
    
    // Get ready (starting countdown)
    function startCountDown() {
    	var counter = 8;
    	// For each interval, 1 second delay
    	var interval = setInterval(function() {
    		if (counter < 6) {
    			$('#countdown').text(counter);
    		}
    	    counter--;
    	    // End of timer
    	    if (counter == -1) {
    	        clearInterval(interval);
    	     	// Put action panel visible
            	$("#action-panel").css("visibility", "visible");
    	        // Load data (questions-answers)
    	        startGame();
    	    }
    	}, 1000);
    }
    
   
    function startGame() {
    	   	
    	// Delay for questions to be answered -> 20 seconds + 4 seconds for the correct answer display time
    	var delay1 = 34000;
    	// The first question will be displayed with no delay.
    	var delay1start = 0; 
    	// Delay for the correct questions to be displayed -> 20 seconds (24 - 4)
    	var delay2 = 30000;
    	// Iterate through the 5 questions
    	var questionList = lobbyGroup["questionList"];
    	
		for (var i = 0; i < questionList.length; i++) {
			if (i > 0) {
				showQuestion(questionList[i], delay1, delay2);
			} else {
				showQuestion(questionList[i], delay1start, delay2);
			}
		}
    	
		// Calculate the time that the question game will take
		var delayEndingScore = delay1 * questionList.length;
    	// Ending game: show ranking
    	setTimeout(function(){
    		// Clean screen
    		// Put answer panel invisible
    		$("#question").css("visibility", "hidden");
    		// Put answer panel invisible
    		$("#correct").css("visibility", "hidden");
    		// Put action panel invisible
     		$("#action-panel").css("visibility", "hidden");
    		// Rearrange array by score
    		playerList = sortPlayerListByScore(playerList);
    		// Show ranking scoreboard
    		listRankingScoreboard(playerList);    		
    	}, delayEndingScore);
		
    	// Calculate the time after everything else will happen
    	var delayEndingRematch = delayEndingScore + 5000;
    	// Show leave button or go to rematch depending score
    	setTimeout(function(){
    		var bool = checkRematch();
    		console.log(bool);
    		if (bool === false) {
    			exitLobbyAndShowLeaveButton();
    		} else {
    	    	goToRematch();
    		}
    	}, delayEndingRematch);
    	
    	
    }
    
    /**
     *	Does the whole question cycle:
     *	1. Show question
     *	2. Create 20 seconds timer
     *	3. Show correct answer
     *	4. Recalculate score
     *  @param value:  Question to be displayed
     *  @param delay1: Delay for questions to be answered -> 20 seconds + 4 seconds for the correct answer display time
     *  @param delay2: Delay for the correct questions to be displayed -> 20 seconds (24 - 4)
     *	@param correct answer: containing the correct answer object
     *	@param incorrect_answers {array}: containing all incorrect answers objects
     */
    function showQuestion(value, delay1, delay2) {

    	// Show questions in 20 seconds delay
    	setTimeout(function(){
     		// Show question in html
     		$('#question').html(value["question"]);
     		// Show available options
     		showOptionButtons(value["answerPairs"]);
     		
     		// Clear chosen answer
     		chosenAnswer = "";
     		
    		// Show timer for 20 seconds wait (20 seconds countdown)
    		var count = 28;
    		var interval = setInterval(function() {
    			$('#countdown').text(count);
    			// Stop timer when finishing countdown
    			if (count == 0) {
    				clearInterval(interval);
    				
    				var loop = 0;
    				globalAnswers.forEach(function(value){
    					console.log($( "#droppableContainer"+ loop +" div div"));
    					console.log($( "#droppableContainer"+ loop +" div div").html());
    					
    		    		if ($( "#droppableContainer"+ loop +" div div" ).html() == value[1]) {
    			    		
    			    		$( "#droppableContainer"+ loop +" div div" ).css({"color": "green", "border": "2px solid green"});
    			    		
    		    		}
    		    		else{
			    			
			    			$( "#droppableContainer"+ loop +" div div").css({"color": "red", "border": "2px solid red"});
			    		}
    		    		loop++;
    		    	});
    				
    			}
    			count--;
    		}, 1000);
    		
    		// Show answers in 4 seconds delay
    		setTimeout(function(){
    			// Show answer
    			$('#question').html(value["correct_answer"]);
    			// Recalculate score
    			calculatePlayerScores(value["correct_answer"]);
    			// Send score data throught websocket
    			send(JSON.stringify(scoreObj));
    		}, delay2);
    		offset += delay2;
    	
    	}, delay1 + offset);
    	offset += delay1;
    }
    
    /**
     *	Creates all available options buttons and shows in screen
     *	@param correct answer: containing the correct answer object
     *	@param incorrect_answers {array}: containing all incorrect answers objects
     */
    var orderedAnswers = [];
     var orderedQuestionStarts = [];
     var globalAnswers = [];
    function showOptionButtons(answers) {
    	globalAnswers = answers;
    	orderedAnswers = [];
    	orderedQuestionStarts = [];
    	
    	orderedAnswers.push(answers[0][1]);
    	orderedQuestionStarts.push(answers[0][0]);
		orderedAnswers.push(answers[1][1]);
		orderedQuestionStarts.push(answers[1][0]);
		orderedAnswers.push(answers[2][1]);
		orderedQuestionStarts.push(answers[2][0]);
		orderedAnswers.push(answers[3][1]);
		orderedQuestionStarts.push(answers[3][0]);
		
		
    	
    	
    	// Put all options together in an array
    	var mergedArray = orderedAnswers;
    	orderedAnswers = orderedAnswers;
    	// They need to be scrumbled!
    	mergedArray = shuffle(mergedArray);
    	// Create and show all options as buttons
    	createButtons(mergedArray);
    	showDestinyBlocks(orderedQuestionStarts);
    }
    
    /**
     * Shuffles array in place.
     * @param {Array} a items An array containing the items.
     */
    function shuffle(array) {
        var j, x, i;
        for (i = array.length - 1; i > 0; i--) {
            j = Math.floor(Math.random() * (i + 1));
            x = array[i];
            array[i] = array[j];
            array[j] = x;
        }
        return array;
    }
     
     function allowDrop(ev) {
    	  ev.preventDefault();
    	}

    	function drag(ev) {
    	  ev.dataTransfer.setData("text", ev.target.id);
    	}

    	function drop(ev) {
    	  ev.preventDefault();
    	  var data = ev.dataTransfer.getData("text");
    	  ev.target.appendChild(document.getElementById(data));
    	}
    /**
     * Creates buttons with option text
     * @param {Array} option items
     */
     function createButtons(options) {
     	// Erase previous options if there are any!!!
     	$("#action-panel").empty();
     
     	// Iterate through all options
     	var html;
     	var loop = 0;
     	html = "<div id=\"buttonEncap\">";
     	options.forEach(function(item){
     		// Create the actual butto structure
     		html = html + "<div id=\"answerDiv"+loop+"\" draggable=\"true\" ondragstart=\"drag(event)\" class=\"my-draggable\">" + item + "</div>"; 
     		// Append to action-panel
         	loop++;
     		
     	});
     	html = html + "</div>";
     	$("#action-panel").append(html);
     	
     }
     
     
     function showDestinyBlocks(options) {
     	// Erase previous options if there are any!!!
     
     	// Iterate through all options
     	orderedQuestionStarts = options;
     	var html = "<div id=\"destinyEncap\">";
     	var loop = 0;
     	options.forEach(function(item){
     		// Create the actual butto structure
     		html = html + "<div class=\"droppableContainer\" id=\"droppableContainer" + loop +"\"><p>" + item + "</p> <div ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\" id=\"destinyContainer\" class=\"droppable\" ></div></div>"; 
			loop++;
     		
     	});
     	html = html + "</div>";
     	$("#action-panel").append(html);
     	
     }
     
    /**
     *	Save the chosen answer on click into local variable
     */
    $(document).on('click', '#option', function () {
    	// Save chosen answer
    	var $this = $(this); // Just for performance
    	chosenAnswer = $(this).text();
    	// This content has carriage returns and or inline feeds. $.trim removes this
    	chosenAnswer = $.trim(chosenAnswer);
    	
    	// Set rest of buttons as default    	
    	$(".option").removeClass('my-button-text-selected');
    	
    	// Change clicked button color in order to visualize selected option
    	$this.addClass("my-button-text-selected");
    });
    
    /**
     *	Creates buttons with option text
     *	@param {Array} correctAnswer contains the correct answer from the actual question
     */
    function calculatePlayerScores(correctAnswer) {
    	// First we set the correct answer on hidden element, in order to retrieve string value as UTF-8
    	var punctuation = 0;
    	var loop = 0;
    	
    	
    	globalAnswers.forEach(function(value){
    			if ($( "#droppableContainer"+ loop +" div div" ).html() == value[1]) {
	    			
	    			punctuation += 1;
	    		}
    		loop++;
    	});
    	
    	
    	scoreObj.score = scoreObj.score + punctuation;
    	
    }
    
    /**
     *	Creates a table as final ranking (similar to players Scoreboard)
     *	@param {Array} containing player object items
     */
    function listRankingScoreboard(playerList) {
    	// Remove old info
    	$("#scoreboard").empty();
    	
    	// Add head
    	var head = '<thead> <tr>';
    	head += '<th> Ranking </th>';
    	head += '<th> Player </th>';
    	head += '<th> Score </th>';
    	head += '</tr> </thead>';
    	$("#scoreboard").append(head);
    	// Add boddy
    	var body = '<tbody>';
    	var place = 1;
    	playerList.forEach(function(obj){ // For each row
    		// Each column
    		var row = '<tr>' + '<td>' + place + '</td>';
    		row += '<td>' + obj["username"] + '</td>';
    		row += '<td>' + obj["score"] + '</td>';
    		body += row + '</tr>';
    		// Increment podium ranking place
    		place ++;
    	});
    	// Append generated rows/columns to table
    	$("#scoreboard").append(body+'</tbody>');
    }
     
    /**
      *	Sorts the players list from higest score to lowest score
      *	@param {Array} containing player object items
      */
    function sortPlayerListByScore(playerList) {
    	playerList = playerList.sort(function(a, b) {
    		return b.score - a.score;
    	});
   		return playerList;
    }
    
    /**
      *	Check if actual player has the same max score as another player
      * If it is able to take a rematch
      */
    function checkRematch() {
    	var myScore = scoreObj.score;
    	// Copy players array without current player, in order to compare others' score with own 
    	var players = [];
    	for (var i = 0; i < playerList.length; i++) {
    		if (playerList[i]["id"] != userId) {
    			players.push(playerList[i]);
    		}
    	}
    	// Get the max score value from other players
    	var maxScore = 0;
    	var bool = false;
    	if (players.length > 0) {
    		var maxScore = Math.max.apply(Math, players.map(function(i) { return i.score; }));
    		if (myScore == maxScore) {
        		bool = true;
        	}
    	}    	
    	return bool;
    }
    
    /**
      * First exit lobbyGroup then show leave button X
      * Trigger leave button
      */
    function exitLobbyAndShowLeaveButton() {
    	// Redirect to new page (this game lobbies list)
        var href = $('#leaveLobbyBtn').attr('href');
		window.location.href = href; //causes the browser to refresh and load the requested url
    }
    
    /**
     * Redirect to another page after asking for new questions
     * With a timer
     */
    function goToRematch() {
    	// Redirect to new page (this same game)
	    var href = $('#startGame').attr('href');
	    var infoObj = {
	    	'action'	: "rematch",
	    	'lobbyId'	: lobbyId
	    };
	    send(JSON.stringify(infoObj));
		window.location.href = href; //causes the browser to refresh and load the requested url   		
   	}
    
    /**
     *	Do on page load. Start game (countdown)
     */
    $(document).ready(startCountDown());
    
</script>

<div class="container">
    <div class="row">
    	<div id="progressTimer"></div>
    </div>
    
    <div class="row">		
		
		<div id="countdown">
			Get ready!
		</div>
		
		<span id="question">
			
		</span>
		
	</div>
	
	<div class="row">

		<div id="action-panel" style="visibility: hidden;">
			<!-- ALL OPTIONS -->
		</div>
		
		<span id="correct" style="visibility: hidden;">
			<!-- HIDDEN correct option -->
		</span>
		
		
		
		<div class="col-sm">
    		<h3 id="scoreboard">
	    		<spring:message code="label.scoreBoard" />
		    </h3>
		    <table id="scoreboard"></table>
   		</div>
		
	</div>
	
	<div class="row">
		
		<!-- Hidden link to game lobbies exit -->
		<a id="leaveLobbyBtn" style="display: none;" href="${pageContext.request.contextPath}/lobby/qaScore?id=${lobby.getId()}"></a>
		
		<!-- Hidden link to game  -->
		<a id="startGame" style="display: none;" href="${pageContext.request.contextPath}/lobby/game?id=${lobby.getId()}"></a>
	
	</div>
   
</div>
