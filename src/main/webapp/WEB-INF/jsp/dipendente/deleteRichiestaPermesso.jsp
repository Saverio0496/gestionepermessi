<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>

	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	
	   <title>Rimuovi Elemento</title>
	   
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class='card'>
					    <div class='card-header'>
					        <h5>Rimuovi Richiesta permesso</h5>
					    </div>
					    
					
					    <div class='card-body'>
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Id:</dt>
							  <dd class="col-sm-9">${delete_richiestapermesso_attr.id}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Tipo Permesso:</dt>
							  <dd class="col-sm-9">${delete_richiestapermesso_attr.tipoPermesso}</dd>
					    	</dl>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Data Inizio:</dt>
							  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${delete_richiestapermesso_attr.dataInizio}" /></dd>
					    	</dl>
					    	
					    	<c:if test="${delete_richiestapermesso_attr.dataFine != null }">
						    	<dl class="row">
								  <dt class="col-sm-3 text-right">Data Fine:</dt>
								  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${delete_richiestapermesso_attr.dataFine}" /></dd>
						    	</dl>
					    	</c:if>
					    	
					    	<dl class="row">
							  <dt class="col-sm-3 text-right">Note:</dt>
							  <dd class="col-sm-9">${delete_richiestapermesso_attr.note}</dd>
					    	</dl>
					    	
					    	<c:if test="${delete_richiestapermesso_attr.tipoPermesso == 'MALATTIA' }">
						    	<dl class="row">
								  <dt class="col-sm-3 text-right">Codice Certificato:</dt>
								  <dd class="col-sm-9">${delete_richiestapermesso_attr.codiceCertificato}</dd>
						    	</dl>
						    	<dl class="row">
								  <dt class="col-sm-3 text-right">Allegato:</dt>
								  <dd class="col-sm-9">${delete_richiestapermesso_attr.attachment}</dd>
						    	</dl>
					    	</c:if>
					    </div>
					    
					    <div class='card-footer'>
					        <a href="${pageContext.request.contextPath}/dipendente/listRichiestaPermesso" class='btn btn-outline-secondary' style='width:80px'>
					            <i class='fa fa-chevron-left'></i> Back
					        </a>
					        <form method="post" action="${pageContext.request.contextPath}/dipendente/removeRichiestaPermesso">
								<input type="hidden" name="idRichiestaPermesso" value="${delete_richiestapermesso_attr.id}">
								<input type="submit" value="Conferma">
					        </form>
					    </div>
					<!-- end card -->
					</div>	
			  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>