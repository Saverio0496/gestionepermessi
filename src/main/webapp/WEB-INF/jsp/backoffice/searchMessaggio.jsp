<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			
			<div class='card'>
			    <div class='card-header'>
			        <h5>Ricerca messaggi</h5> 
			    </div>
			    <div class='card-body'>
	
						<form method="post" action="${pageContext.request.contextPath}/backoffice/listForSearchMessaggio" class="row g-3">
						
							<div class="col-md-6">
								<label for="testo" class="form-label">Testo</label>
								<input type="text" name="testo" id="testo" class="form-control" placeholder="Inserire il testo" >
							</div>
							
							<div class="col-md-6">
								<label for="oggetto" class="form-label">Oggetto</label>
								<input type="text" name="oggetto" id="oggetto" class="form-control" placeholder="Inserire l'oggetto" >
							</div>
							
							<div class="col-md-6">
								<label for="dataInserimento" class="form-label">Data Inserimento</label>
                        		<input class="form-control" id="dataInserimento" type="date" placeholder="dd/MM/yy"
                            		title="formato : gg/mm/aaaa"  name="dataInserimento" >
							</div>
							
							<div class="col-md-6">
								<label for="dataLettura" class="form-label">Data Lettura</label>
                        		<input class="form-control" id="dataLettura" type="date" placeholder="dd/MM/yy"
                            		title="formato : gg/mm/aaaa"  name="dataLettura" >
							</div>
							
							<div class="col-md-3">
									<label for="letto" class="form-label">Letto </label>
								    <select class="form-select" id="letto" name="letto" >
								      	<option value="true" >LETTO</option>
								      	<option value="false"  >NON LETTO</option>
								    </select>
								</div>
							
							
							<div class="col-12">	
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
							</div>
	
							
						</form>
			    
				<!-- end card-body -->			   
			    </div>
			</div>	
	
		</div>
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>