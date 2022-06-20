<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100" >
<head>
	<jsp:include page="../header.jsp" />
	<title>Ricerca</title>
	
    
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp"></jsp:include>
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  <div class="container">
	
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}" role="alert">
			  ${errorMessage}
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
			
			<div class="alert alert-warning alert-dismissible fade show d-none" role="alert">
			  Attenzione!!! Funzionalità ancora non implementata. Sulla 'Conferma' per il momento parte il 'listAll'
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			
			<div class='card'>
			    <div class='card-header'>
			        <h5>Ricerca richiesta permesso</h5> 
			    </div>
			    <div class='card-body'>
	
						<form:form modelAttribute="search_richiestapermesso_attr" method="post" action="${pageContext.request.contextPath}/backoffice/listForSearchRichiestaPermesso" novalidate="novalidate" class="row g-3">
						
							<div class="col-md-3">
									<label for="tipoPermesso" class="form-label">Tipo Permesso</label>
								    <spring:bind path="tipoPermesso">
									    <select class="form-select ${status.error ? 'is-invalid' : ''}" id="tipoPermesso" name="tipoPermesso" required>
									    	<option value="" selected> - Selezionare - </option>
									      	<option value="FERIE" ${search_richiestapermesso_attr.tipoPermesso == 'FERIE'?'selected':''} >FERIE</option>
									      	<option value="MALATTIA" ${search_richiestapermesso_attr.tipoPermesso == 'MALATTIA'?'selected':''} >MALATTIA</option>
									    </select>
								    </spring:bind>
								    <form:errors  path="tipoPermesso" cssClass="error_field" />
								</div>
								
								<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDateInizio" type='date' value='${search_richiestapermesso_attr.dataInizio}' />
								<div class="col-md-3">
									<label for="dataInizio" class="form-label">Data Inizio </label>
                        			<spring:bind path="dataInizio">
	                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataInizio" type="date" placeholder="dd/MM/yy"
	                            		title="formato : gg/mm/aaaa"  name="dataInizio" required 
	                            		value="${parsedDateInizio}" >
		                            </spring:bind>
	                            	<form:errors  path="dataInizio" cssClass="error_field" />
								</div>
								
								<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDateFine" type='date' value='${search_richiestapermesso_attr.dataFine}' />
								<div class="col-md-3">
									<label for="dataFine" class="form-label">Data Fine </label>
                        			<spring:bind path="dataFine">
	                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataFine" type="date" placeholder="dd/MM/yy"
	                            		title="formato : gg/mm/aaaa"  name="dataFine" required 
	                            		value="${parsedDateFine}" >
		                            </spring:bind>
	                            	<form:errors  path="dataFine" cssClass="error_field" />
								</div>
								
								<div class="col-md-6 " id="codFis">
									<label for="codiceCertificato" class="form-label">Codice Certificato </label>
									<spring:bind path="codiceCertificato">
										<input type="text" name="codiceCertificato" id="codiceCertificato" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire il codice certificato" value="${search_richiestapermesso_attr.codiceCertificato }" >
									</spring:bind>
									<form:errors  path="codiceCertificato" cssClass="error_field" />
								</div>
								
								<div class="col-md-6">
									<label for="dipendenteSearchInput" class="form-label">Dipendente:</label>
									<spring:bind path="dipendenteDTO">
										<input class="form-control ${status.error ? 'is-invalid' : ''}" type="text" id="dipendenteSearchInput"
											name="dipendenteInput" value="${search_richiestapermesso_attr.dipendenteDTO.nome}${empty search_richiestapermesso_attr.dipendenteDTO.nome?'':' '}${search_richiestapermesso_attr.dipendenteDTO.cognome}">
									</spring:bind>
									<input type="hidden" name="dipendenteId" id="dipendenteId" value="${search_richiestapermesso_attr.dipendenteDTO.id}">
									<form:errors  path="dipendenteDTO" cssClass="error_field" />
								</div>
							
							<div class="col-12">	
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
							</div>
						</form:form>
						
						<%-- FUNZIONE JQUERY UI PER AUTOCOMPLETE --%>
								<script>
									$("#dipendenteSearchInput").autocomplete({
										 source: function(request, response) {
											 	//quando parte la richiesta ajax devo ripulire registaId
											 	//altrimenti quando modifico il campo, cancellando
											 	//qualche carattere, mi rimarrebbe comunque valorizzato il 
											 	//'vecchio id'
											 	$('#dipendenteId').val('');
											 	
										        $.ajax({
										            url: "${pageContext.request.contextPath}/backoffice/searchDipendentiAjax",
										            datatype: "json",
										            data: {
										                term: request.term,   
										            },
										            success: function(data) {
										                response($.map(data, function(item) {
										                    return {
											                    label: item.label,
											                    value: item.value
										                    }
										                }))
										            }
										        });
										    },
										//quando seleziono la voce nel campo deve valorizzarsi la descrizione
									    focus: function(event, ui) {
									        $("#dipendenteSearchInput").val(ui.item.label);
									        return false;
									    },
									    minLength: 2,
									    //quando seleziono la voce nel campo hidden deve valorizzarsi l'id
									    select: function( event, ui ) {
									    	$('#dipendenteId').val(ui.item.value);
									    	//console.log($('#registaId').val())
									        return false;
									    }
									});
								</script>
								<!-- end script autocomplete -->	
			    
				<!-- end card-body -->			   
			    </div>
			</div>	
	
		</div>
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
