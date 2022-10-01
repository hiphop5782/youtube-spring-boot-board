<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
  	<head>
    	<meta charset="utf-8">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<title>자유게시판</title>
<!--     	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet"> -->
    	<link href="https://cdnjs.cloudflare.com/ajax/libs/bootswatch/5.2.1/cerulean/bootstrap.min.css" rel="stylesheet">
    	
    	<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" rel="stylesheet">
    	<style>
    		.empty-space {
    			height:50px;
    		}
    	</style>
  	</head>
  	<body>
    	<!-- navbar -->
    	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		  	<div class="container-fluid">
		    	<a class="navbar-brand" href="#">The Fiery Teacher</a>
		    	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor02" aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation">
		      		<span class="navbar-toggler-icon"></span>
		    	</button>
		    	<div class="collapse navbar-collapse" id="navbarColor02">
		      		<ul class="navbar-nav me-auto">
			        	<li class="nav-item active">
			          		<a class="nav-link" href="#">Home
			            		<span class="visually-hidden">(current)</span>
			          		</a>
			        	</li>
			        	<li class="nav-item">
			         		<a class="nav-link" href="#">Board</a>
			        	</li>
				        <li class="nav-item">
							<a class="nav-link" href="#">About</a>
			        	</li>
			      	</ul>
			      	<form class="d-flex">
			        	<input class="form-control me-sm-2" type="text" placeholder="Search">
			        	<button class="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
			      	</form>
			    </div>
		  	</div>
		</nav>
		
		<div class="empty-space"></div>
		<div class="empty-space"></div>