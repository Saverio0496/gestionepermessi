<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="it" class="h-100">
	<head>
		<jsp:include page="../header.jsp" />
		
	    <style>
		    .error_field {
		        color: red; 
		    }
		</style>
		<title>Modifica Elemento</title>
	</head>
	<body class="d-flex flex-column h-100">
		<jsp:include page="../navbar.jsp" />
		
			<!-- Begin page content -->
			<main class="flex-shrink-0">
				<div class="container">
		
					<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="edit_regista_attr">
						<%-- alert errori --%>
						<div class="alert alert-danger " role="alert">
							Attenzione!! Sono presenti errori di validazione
						</div>
					</spring:hasBindErrors>
				
					<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
					
					<div class='card'>
					    <div class='card-header'>
					        <h5>Modifica elemento</h5> 
					    </div>
					    <div class='card-body'>
				    
				    		<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
							<form:form  enctype="multipart/form-data" modelAttribute="edit_richiestapermesso_attr"  method="post" action="${pageContext.request.contextPath }/dipendente/updateRichiestaPermesso" novalidate="novalidate" class="row g-3">
								<form:hidden path="id"/>
								
								<div class="col-md-12"> 
 										<label for="tipoPermesso" class="form-label">Tipo Permesso: <span class="text-danger">*</span></label> 
 									    <spring:bind path="tipoPermesso"> 
 										    <select class="form-select ${status.error ? 'is-invalid' : ''}" id="tipoPermesso" name="tipoPermesso" required> 
 										    	<option value="" selected> - Selezionare - </option> 
 										      	<option value="FERIE" ${edit_richiestapermesso_attr.tipoPermesso == 'FERIE'?'selected':''} >FERIE</option> 
 										      	<option value="MALATTIA" ${edit_richiestapermesso_attr.tipoPermesso == 'MALATTIA'?'selected':''} >MALATTIA</option> 
 										    </select>
 									    </spring:bind> 
 									    <form:errors  path="tipoPermesso" cssClass="error_field" /> 
 									</div> 
								
 									<div class="col-md-6" id= "codiceCertificato"> 
 										<label for="codiceCertificato" class="form-label">Codice Certificato <span class="text-danger">*</span></label> 
 										<spring:bind path="codiceCertificato"> 
 											<input type="text" name="codiceCertificato" id="codiceCertificato" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire il codice del certificato" value="${edit_richiestapermesso_attr.codiceCertificato }">
 										</spring:bind> 
 										<form:errors  path="codiceCertificato" cssClass="error_field" /> 
 									</div> 
										
 									  <div class="col-md-6" id="attachment"> 
 									    <label for="attachment" class="form-label">Certificato allegato</label> 
 									    <spring:bind path="attachment"> 
 											<input type="file" name="attachment" id="attachment" class="form-control-file" placeholder="Inserire il certificato" value="${edit_richiestapermesso_attr.attachment }"> 
<!--  												<input class="form-control" type="file" id="attachment" name="attachment" value=""> -->
 										</spring:bind>
 									  </div> 
 									
									
 									<div class="col-md-12"> 
 									  <input class="form-check-input" type="checkbox"  id="giornoSingolo" name="giornoSingolo"> 
 									  <label class="form-check-label" for="giornoSingolo">Giorno Singolo</label> 
 									</div> 
								
 									
 										<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDateInizio" type='date' value='${edit_richiestapermesso_attr.dataInizio}' />
										<div class="col-md-3">
											<label for="dataInizio" class="form-label">Data Inizio <span class="text-danger">*</span></label>
		                        			<spring:bind path="dataInizio">
			                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataInizio" type="date" placeholder="dd/MM/yy"
			                            		title="formato : gg/mm/aaaa"  name="dataInizio" required 
			                            		value="${parsedDateInizio}" >
				                            </spring:bind>
			                            	<form:errors  path="dataInizio" cssClass="error_field" />
										</div>
										
										<fmt:formatDate pattern='yyyy-MM-dd' var="parsedDateFine" type='date' value='${edit_richiestapermesso_attr.dataFine}' />
										<div class="col-md-3">
											<label for="dataFine" class="form-label">Data Fine <span class="text-danger">*</span></label>
		                        			<spring:bind path="dataFine">
			                        		<input class="form-control ${status.error ? 'is-invalid' : ''}" id="dataFine" type="date" placeholder="dd/MM/yy"
			                            		title="formato : gg/mm/aaaa"  name="dataFine" required 
			                            		value="${parsedDateFine}" >
				                            </spring:bind>
											<form:errors  path="dataFine" cssClass="error_field" />
										</div>
 									<div >
										<label for="note" class="form-label">Note</label>
										<spring:bind path="note">
											<textarea class="form-control rounded-0" id="note" rows="3" name="note"></textarea>
										</spring:bind>
									</div>
									
								<div class="col-12">	
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								</div>
		
							</form:form>
							
							
							<script type="text/javascript">
								$(document).ready(function(){
									if($("#giornoSingolo").is(':checked')){
										
										$("#dataFine").attr("disabled","disabled");
									}
									else{
										$("#dataFine").removeAttr("disabled");
									}
									
									$("#giornoSingolo").change(function() {
										
										if($("#giornoSingolo").is(':checked')){
											
											$("#dataFine").attr("disabled","disabled");
										}
										else{
											$("#dataFine").removeAttr("disabled");
										}
									});
									
								});
								
								$(document).ready(function(){
									if($("#tipoPermesso").val() == "MALATTIA"){
										
										$("#codiceCertificato").show();
										$("#attachment").show();
									}
									else{
										
										$("#codiceCertificato").hide();
										$("#attachment").hide();
									}
									
									$("#tipoPermesso").change(function() {
										
										if($("#tipoPermesso").val() == "MALATTIA"){
											
											$("#codiceCertificato").show();
											$("#attachment").show();
										}
										else{
											
											$("#codiceCertificato").hide();
											$("#attachment").hide();
										}
									});
									
								});
							</script>
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>	
			
			<!-- end container -->	
			</div>	
		</main>
		<jsp:include page="../footer.jsp" />
		
	</body>
</html>