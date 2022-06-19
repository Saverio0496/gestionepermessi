<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>

	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	
	   <title>Elimina Richiesta Permesso</title>
	   
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Visualizza dettaglio</h5>
					    </div>
					    
					
					    <div class='card-body'>
					    
					    	<form:form method="post" action="${pageContext.request.contextPath}/dipendente/removeRichiestaPermesso" class="row g-3" novalidate="novalidate">
					    
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Id:</dt>
							  <dd class="col-sm-9">${delete_richiestapermesso_attr.id}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Codice Certificato:</dt>
							  <dd class="col-sm-9">${delete_richiestapermesso_attr.codiceCertificato}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data Inizio:</dt>
							  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${delete_richiestapermesso_attr.dataInizio}" /></dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data Fine:</dt>
							  <dd class="col-sm-9"><fmt:formatDate type="date" value = "${delete_richiestapermesso_attr.dataFine}" /></dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Note:</dt>
							  <dd class="col-sm-9">${delete_richiestapermesso_attr.note}</dd>
			    			</dl>
			    	
			    			<dl class="row">
							  <dt class="col-sm-3 text-right">Stato:</dt>
							  <dd class="col-sm-9">${delete_richiestapermesso_attr.approvato?'APPROVATO':'NON APPROVATO' }</dd>
			    			</dl>
					    	
					    	<input type="hidden" name = "idRichiestapermesso" value = "${delete_richiestapermesso_attr.id}">
					    	
					    	<div class="col-12">
								<input type="submit" name="submit" value="Conferma" id="submit" class="btn btn-outline-danger">
							</div>
					    	
					    	</form:form>
					    </div>
					    <!-- end card body -->
					    
					    
					    
					    
					<!-- end card -->
					</div>	
			  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>