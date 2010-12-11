<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Honeynet IM Monitoring Service</title>
	<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.7.custom.css" rel="stylesheet" />
	<link type="text/css" href="css/global.css" rel="stylesheet" />
	<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.7.custom.min.js"></script>

</head>
<body class="oneColFixCtrHdr">

<div id="container">
  <div id="header">
    <h1>Honeynet IM Monitoring Service</h1>
  </div><!-- / #header -->
  <div id="mainContent">
    <h2>Service Listing</h2>
    <p>Below is a list of the supported services this monitoring service submits to.</p>
	<table>
		<thead>
			<tr><th>Service</th><th>In Processing</th><th>Completed</th><th>Errors</th></tr>
		</thead>
		<tbody>
			<tr>
				<td><a href="<s:url value="wepawet" />">Wepawet</a></td>
				<td><s:property value="wepawetStatus.processing" /></td>
				<td><s:property value="wepawetStatus.results" /></td>
				<td><s:property value="wepawetStatus.errors" /></td>
			</tr>
		</tbody>
	</table>
  </div><!-- / #mainContent -->

</div><!-- / #container -->
</body>
</html>