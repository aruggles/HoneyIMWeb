<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="refresh" content="30" />  
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
    <h2>Wepawet Service</h2>
    <p>Below are the details to the submissions to the Wepawet malware analysis.</p>
    <div id="tabs">
	    <ul>
	    	<li><a href="#results">Results</a></li>
	    	<li><a href="#errors">Errors</a></li>
	    	<li><a href="#processing">Processing</a></li>
	    </ul>
	    <div id="results">
	    	<s:if test="resultList.isEmpty()">
	    		<p>There are no results to display.</p>
	    	</s:if><s:else>
		    	<table>
					<thead>
						<tr><th>Result</th><th>Date</th><th>Report</th></tr>
					</thead>
					<s:iterator value="resultList">
					<tbody>
						<tr>
							<td style="width: 10%;"><s:property value="result" /></td>
							<td style="width: 40%;"><s:date name="created" /></td>
							<td style="width: 50%;"><a href="<s:property value="report" />"><s:property value="report" /></a></td>
						</tr>
					</tbody>
					</s:iterator>
				</table>
			</s:else>
	    </div>
	    <div id="errors">
	    	<s:if test="errorList.isEmpty()">
	    		<p>There are no errors to display.</p>
	    	</s:if><s:else>
		    	<table>
					<thead>
						<tr><th>Code</th><th>Date</th><th>Message</th></tr>
					</thead>
					<s:iterator value="errorList">
					<tbody>
						<tr>
							<td><s:property value="code" /></td>
							<td><s:date name="created" /></td>
							<td><s:property value="message" /></td>
						</tr>
					</tbody>
					</s:iterator>
				</table>
			</s:else>
	    </div>
	    <div id="processing">
	    	<s:if test="procList.isEmpty()">
	    		<p>There is nothing currently being processed.</p>
	    	</s:if><s:else>
		    	<table>
					<thead>
						<tr><th>Hash</th><th>Date</th><th>Status</th></tr>
					</thead>
					<s:iterator value="procList">
					<tbody>
						<tr>
							<td><s:property value="hash" /></td>
							<td><s:date name="created" /></td>
							<td><s:property value="status" /></td>
						</tr>
					</tbody>
					</s:iterator>
				</table>
			</s:else>
	    </div>
	</div>
  </div><!-- / #mainContent -->

</div><!-- / #container -->
<script type="text/javascript">
	$(function() {
		$( "#tabs" ).tabs();
	});
</script>
</body>
</html>