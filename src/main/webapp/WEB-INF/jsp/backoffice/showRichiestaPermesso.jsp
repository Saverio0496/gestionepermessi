<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<!-- Common imports in pages -->
	<jsp:include page="../header.jsp" />
	<title>Visualizza Richiesta Permesso</title>
	
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  	<div class="container">
			
			<div class='card'>
			    <div class='card-header'>
			        Visualizza dettaglio richiesta permesso
			    </div>
			
			    <div class='card-body'>
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Id:</dt>
					  <dd class="col-sm-9">${show_richiestapermesso_attr.id}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Codice Certificato:</dt>
					  <dd class="col-sm-9">${show_richiestapermesso_attr.codiceCertificato}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Data Inizio:</dt>
					  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_richiestapermesso_attr.dataInizio}" /></dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Data Fine:</dt>
					  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_richiestapermesso_attr.dataFine}" /></dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Note:</dt>
					  <dd class="col-sm-9">${show_richiestapermesso_attr.note}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Stato:</dt>
					  <dd class="col-sm-9">${show_richiestapermesso_attr.approvato?'APPROVATO':'NON APPROVATO' }</dd>
			    	</dl>
			    	
			    	<!-- info Dipendente -->
			    	<p>
					  <a class="btn btn-primary btn-sm" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
					    Info Dipendente
					  </a>
					</p>
					<div class="collapse" id="collapseExample">
					  <div class="card card-body">
					  	<dl class="row">
						  <dt class="col-sm-3 text-right">Nome:</dt>
						  <dd class="col-sm-9">${show_richiestapermesso_attr.dipendente.nome}</dd>
					   	</dl>
					   	<dl class="row">
						  <dt class="col-sm-3 text-right">Cognome:</dt>
						  <dd class="col-sm-9">${show_richiestapermesso_attr.dipendente.cognome}</dd>
					   	</dl>
					   	<dl class="row">
						  <dt class="col-sm-3 text-right">Codice Fiscale:</dt>
						  <dd class="col-sm-9">${show_richiestapermesso_attr.dipendente.codFis}</dd>
					   	</dl>
					   	<dl class="row">
						  <dt class="col-sm-3 text-right">Email:</dt>
						  <dd class="col-sm-9">${show_richiestapermesso_attr.dipendente.email}</dd>
					   	</dl>
					   	<dl class="row">
						  <dt class="col-sm-3 text-right">Data Nascita:</dt>
						  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_richiestapermesso_attr.dipendente.dataNascita}" /></dd>
					   	</dl>
					   	<dl class="row">
						  <dt class="col-sm-3 text-right">Data Assunzione:</dt>
						  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_richiestapermesso_attr.dipendente.dataAssunzione}" /></dd>
					   	</dl>
					   	<dl class="row">
						  <dt class="col-sm-3 text-right">Data Dimissioni:</dt>
						  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_richiestapermesso_attr.dipendente.dataDimissioni}" /></dd>
					   	</dl>
					   	<dl class="row">
						  <dt class="col-sm-3 text-right">Sesso:</dt>
						  <dd class="col-sm-9">${show_richiestapermesso_attr.dipendente.sesso}</dd>
					   	</dl>
					    
					  </div>
					<!-- end info Regista -->
			    	
			    <!-- end card body -->
			    </div>
			    
			    <div class='card-footer'>
			        <a href="${pageContext.request.contextPath }/backoffice/listRichiestePermesso" class='btn btn-outline-secondary' style='width:80px'>
			            <i class='fa fa-chevron-left'></i> Back
			        </a>
			    </div>
			<!-- end card -->
			</div>	
	
		<!-- end container -->  
		</div>
		
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>