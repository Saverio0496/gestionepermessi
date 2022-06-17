<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<header>
  <!-- Fixed navbar -->
 <nav class="navbar navbar-expand-lg navbar-dark bg-primary" aria-label="Eighth navbar example">
    <div class="container">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample07" aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExample07">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/home">Home</a>
          </li>
            <sec:authorize access="hasRole('ADMIN')">
		      	<li class="nav-item dropdown">
		        	<a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Utenze</a>
		        	<div class="dropdown-menu" aria-labelledby="dropdown01">
		        		<a class="dropdown-item" href="${pageContext.request.contextPath}/admin">Lista Utenti</a>
		          		<a class="dropdown-item" href="${pageContext.request.contextPath}/admin/searchUtente">Ricerca Utenti</a>
		        	</div>
		      	</li>
		       	<li class="nav-item">
            		<a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/admin/searchDipendente">Ricerca Dipendenti</a>
          		</li>
		   </sec:authorize>
		   <sec:authorize access="hasRole('BO_USER')">
		      	<li class="nav-item dropdown">
		        	<a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Dipendenti</a>
		        	<div class="dropdown-menu" aria-labelledby="dropdown01">
		        		<a class="dropdown-item" href="${pageContext.request.contextPath}/backoffice/listDipendente">Lista Dipendenti</a>
		          		<a class="dropdown-item" href="${pageContext.request.contextPath}/backoffice/searchDipendente">Ricerca Dipendenti</a>
		          		<a class="dropdown-item" href="${pageContext.request.contextPath}/backoffice/insertDipendente">Inserisci Dipendente</a>
		        	</div>
		      	</li>
		      	<li class="nav-item">
            		<a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/richiestapermesso/search">Ricerca Richieste Permesso</a>
          		</li>
          		<li class="nav-item dropdown">
		        	<a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Messaggi</a>
		        	<div class="dropdown-menu" aria-labelledby="dropdown01">
		        		<a class="dropdown-item" href="${pageContext.request.contextPath}/messaggio/">Lista Messaggi</a>
		          		<a class="dropdown-item" href="${pageContext.request.contextPath}/messaggio/search">Ricerca Messaggi</a>
		        	</div>
		      	</li>
		   </sec:authorize>
		   <sec:authorize access="hasRole('DIPENDENTE_USER')">
		      	<li class="nav-item dropdown">
		        	<a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Richieste Permesso</a>
		        	<div class="dropdown-menu" aria-labelledby="dropdown01">
		        		<a class="dropdown-item" href="${pageContext.request.contextPath}/richiestapermesso/">Lista Richieste Permessi</a>
		          		<a class="dropdown-item" href="${pageContext.request.contextPath}/richiestapermesso/search">Ricerca Richieste Permessi</a>
		          		<a class="dropdown-item" href="${pageContext.request.contextPath}/richiestapermesso/insert">Inserisci Richiesta Permesso</a>
		        	</div>
		      	</li>
		   </sec:authorize>
        </ul>
      </div>
      <sec:authorize access="isAuthenticated()">
	      <div class="col-md-3 text-end">
	        <p class="navbar-text">Utente: ${userInfo.username} (${userInfo.getDipendenteDTO().nome} ${userInfo.getDipendenteDTO().cognome})
	      </div>
      </sec:authorize>
      <div>
      <div>
       <div class="collapse navbar-collapse text-right" id="navbarsExample07">
      	<ul class="navbar-nav me-auto mb-2 mb-lg-0">
      		   <sec:authorize access="isAuthenticated()">
			   <li class="nav-item dropdown ">
		        <a class="nav-link dropdown-toggle " href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Account</a>
		        <div class="dropdown-menu " aria-labelledby="dropdown01">
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a>
		          <a class="dropdown-item"  href="${pageContext.request.contextPath}/reset">Reset Password</a>
		        </div>
		      </li>
      		</sec:authorize>	
      	</ul>
      </div>
     </div>
    </div>
  </div>
  </nav>
 
  
</header>
