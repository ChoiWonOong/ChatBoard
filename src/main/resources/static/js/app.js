
var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    console.log("sendname");
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    console.log("show");
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function login(){
    $.ajax({
        url:"/login",
        type :  "POST",
        dataType : "json",
        data : {
            username : $("#username").val(),
            password : $("#password").val()
        },
        beforeSend : function(xhr)
        {
            //이거 안하면 403 error
            //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
            var $token = $("#token");
            xhr.setRequestHeader($token.data("token-name"), $token.val());
        },
        success : function(response){
            if(response.code == "200"){
                // 정상 처리 된 경우
                window.location = response.item.url;	//이전페이지로 돌아가기
            } else {
                alert(response.message);
            }
        },
        error : function(a,b,c){
            console.log(a,b,c);
        }
    })
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#login").click( function() { login(); })
});
