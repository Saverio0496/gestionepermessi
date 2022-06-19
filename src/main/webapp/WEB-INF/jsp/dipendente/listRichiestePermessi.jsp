<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<jsp:include page="../header.jsp" />
	<title>Pagina dei risultati</title>
	
</head>
<body class="d-flex flex-column h-100">
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  <div class="container">
	
			<div class="alert alert-success alert-dismissible fade show  ${successMessage==null?'d-none':'' }" role="alert">
			  ${successMessage}
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
			  ${errorMessage}
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			
			<div class='card'>
			    <div class='card-header'>
			        <h5>Lista delle richieste permessi</h5> 
			    </div>
			    		<div class='card-body'>
			    		<a class="btn btn-primary " href="${pageContext.request.contextPath}/dipendente/insertRichiestaPermesso">Add New</a>
			    		<a href="${pageContext.request.contextPath}/dipendente/searchRichiestaPermesso" class='btn btn-outline-secondary' >
				            <i class='fa fa-chevron-left'></i> Torna alla Ricerca
				   	 	</a>
			    
			        <div class='table-responsive'>
			            <table class='table table-striped ' >
			                <thead>
			                    <tr>
			                    	<th>Id</th>			                        
			                        <th>Tipo Permesso</th>
			                        <th>Codice Certificato</th>
			                        <th>Data di Inizio</th>
			                        <th>Data di Fine</th>
			                        <th>Stato</th>
			                        <th>Azioni</th>
			                    </tr>
			                </thead>
			                <tbody>
			                	<c:forEach items="${richiestapermesso_dipendente_list_attribute }" var="richiestaPermessoItem">
									<tr>
										<td>${richiestaPermessoItem.id }</td>
										<td>${richiestaPermessoItem.tipoPermesso }</td>
										<td>${richiestaPermessoItem.codiceCertificato }</td>
										<td><fmt:formatDate type="date" value = "${richiestaPermessoItem.dataInizio}" /></td>
										<td><fmt:formatDate type="date" value = "${richiestaPermessoItem.dataFine}" /></td>
										<td>${richiestaPermessoItem.approvato?'APPROVATO':'NON APPROVATO' }</td>
										<td>
											<a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/dipendente/showRichiestaPermesso${richiestaPermessoItem.id}">Visualizza</a>
											<a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/dipendente/editRichiestaPermesso${richiestaPermessoItem.id}">Edit</a>
											<a class="btn  btn-sm btn-outline-danger ml-2 mr-2" href="${pageContext.request.contextPath}/dipendente/deleteRichiestaPermesso${richiestaPermessoItem.id}">Delete</a>
										</td>
									</tr>
								</c:forEach>
			                </tbody>
			            </table>
			        </div>
			   
				<!-- end card-body -->			   
			    </div>
			</div>	
	
		</div>	
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>