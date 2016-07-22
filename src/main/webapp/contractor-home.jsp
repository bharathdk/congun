<%@ page language="java" contentType="text/html; utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>ConGun</title>

<meta name="description" content="Heavy Machinery Rentals">
<meta name="author" content="Congun">

 <!-- CSS -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="css/form-elements-style.css">
        <link rel="stylesheet" href="css/form-style.css">
        <link rel="stylesheet" href="css/home.css">


<script src="js/jquery.min.js" type="text/javascript"></script>
<!-- <script src="http://code.jquery.com/jquery-1.10.0.min.js" type="text/javascript"></script> -->
<script type="text/javascript">
$(document).ready(function(){
	
	var contractorDetails = localStorage.getItem("user_details");
	contractorDetails = $.parseJSON(contractorDetails);
	console.log(contractorDetails);
	$("#user-name").text(contractorDetails.firstname+" "+contractorDetails.lastname);
	
	
	var quotes = [];
	var requirements = [];
	var obj = $.parseJSON('[{"quoteId":"1","mfg":"CAT","model":"2016","rate":"5000"},{"quoteId":"2","mfg":"JCB","model":"2011","rate":"3000"},{"quoteId":"3","mfg":"terrex","model":"2013","rate":"7000"}]');
	 console.log(obj);
	  $.each(obj,function(index,value){
		  quotes[value.quoteId] = value; 
	  });
	
$.ajax({
	url:"user/reqtest",
	type:"get",
	data:{},
	dataType:"json",
	success:function(response){
		var reqList = "";
		console.log(response);
		$.each(response,function(index, value){
			reqList += '<button type="button" class="btn btn-block btn-lg btn-warning" id='+value.id+'>'+value.description+'<br>No Of Quotes : <span class="badge">'+value.number+'</span></button>';
			requirements[value.id] = value;
			/* reqList += '<a href="#requirement-details" id='+value.id+' class="btn btn-lg btn-block btn-warning" type="button">'+value.description+'</a>'; */
		});
		console.log(response);
		console.log(reqList);

		$('#requirement-list').append(reqList);

	}
});


$("#addReq").click(function(){
	$(location).attr('href','requirement-form.html')
});


$(document).delegate( "#panelReq button[type='button']", "click",
		function(e){
	$(location).attr('href','contractorRequirement.html')
});


 $(document).delegate( "#requirement-list button[type='button']", "click",
	    function(e){
	 	var quotePanelContent="";
	    var inputId = this.id;
	    console.log( inputId );
	    $('#content').html("");
	    $.each(obj,function(index,value){
	    var panelId = "panel"+value.quoteId;
	    var panelCollapseId = "collapse"+value.quoteId;
	    quotePanelContent += '<div class="panel-group" id='+panelId+'><div class="panel panel-default"><div class="panel-heading">'+
	    			'<a data-toggle="collapse" data-parent="#'+panelId+'" href="#'+panelCollapseId+'""><div class = "panel-heading-content">'+
	                '<h4 class="panel-title"> Quote'+value.quoteId+'</h4></div></a></div><div id='+panelCollapseId+' class="panel-collapse collapse in">'+
	                '<div class="panel-body">'+value.mfg+','+value.model+','+value.rate+'</div>'+
	                '</div></div></div>';
	    });            
	    			
	    var reqPanelContent = '<div class="panel-group" id=panelReq><div class="panel panel-default"><div class="panel-heading">'+
	    			'<a data-toggle="collapse" data-parent="#panelReq" href="#reqPanelCollapseId"><div class = "panel-heading-content">'+
	                '<h4 class="panel-title">requirement '+requirements[this.id].id+'</h4></div></a></div><div id="reqPanelCollapseId" class="panel-collapse collapse in">'+
	                '<div class="panel-body">'+requirements[this.id].description+'<button type="button" id="editReq" class="btn btn-warning pull-right">edit</button> </div>'+
	                '</div></div></div>';			
	    
	    var html = reqPanelContent + quotePanelContent;
	    $('#content').append(html);
	    }
	);

});
</script>
</head>
<body>
<div class="container-fluid">
<%@include file="header.html" %>
		<div class="main-content"> 		
			<div class="row">
                <div class="col-md-4"> 
                    <ul class="nav nav-tabs"> 
                        <li class="active">
                            <a href="#tab5" data-toggle="tab">Home</a>
                        </li>
                        <li style="cursor:pointer">
                            <a id="addReq">Add Requirement</a>
                        </li>                         
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab5">
                            <ul id="requirement-list">
                            <!--We will have the requirement list dynamically added here --> 
                            </ul>
                        </div>
                    </div>                     
                </div>
                <div class="col-md-8">
                    <div id="content">
                    <!-- For the quotations collapse feature -->
                    </div>	
                </div>
              </div>
         </div>   
<%@include file="footer.html" %>
</div>
<script src="js/bootstrap.min.js"></script>
 <script src="js/jquery.backstretch.min.js"></script>
        <script src="js/retina-1.1.0.min.js"></script>
        <script src="js/quote-scripts.js"></script>
<script src="js/scripts.js"></script>
</body>
</html>