<%@ page import="user.beans.Member" %><%--
  Created by IntelliJ IDEA.
  User: nickm
  Date: 16/03/2023
  Time: 09:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Modifica info personali</title>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#modifyButton").click(chiamaControl);

            function chiamaControl() {

                $.post("MemberControl",
                    {
                        "action": "modify",
                        "oldPassword": $("#oldPassword").val(),
                        "newPassword": $("#newPassword").val(),
                        "name": $("#name").val(),
                        "surname": $("#surname").val()
                    },
                    function (data) {
                        var success = "\"OK\"";
                        var result = (JSON.stringify(data.result)).localeCompare(success);

                        if (result == 0) {
                            $("#mex").html("Modifica effettuata");
                        } else {
                            $("#mex").html(data.result);
                        }
                    });
            }
        });
    </script>
</head>
<body>
<jsp:include page="/header.jsp" flush="true"></jsp:include>
<main class="page hire-me-page">
    <section class="portfolio-block hire-me">
        <div class="container">
            <div class="heading">
                <h2>MODIFICA INFO PERSONALI</h2>
            </div>
            <form id="form-id" onsubmit="return false;">
                <p id="mex"></p>
                <div class="mb-3"><label class="form-label">Nome</label><input class="form-control"
                                                                               type="text"
                                                                               value="<%=((Member) session.getAttribute("memberInSession")).getName()%>"
                                                                               id="name"
                                                                               required="" name="name"
                                                                               pattern="^[a-zA-Z ]+$"
                                                                               oninvalid="this.setCustomValidity('Il campo evidenziato deve avere solo lettere e/o spazi al suo interno')"
                                                                               onchange="this.setCustomValidity('')">
                </div>
                <div class="mb-3"><label class="form-label">Cognome</label><input class="form-control"
                                                                                  type="text"
                                                                                  value="<%=((Member) session.getAttribute("memberInSession")).getSurname()%>"
                                                                                  id="surname" required=""
                                                                                  name="surname"
                                                                                  pattern="^[a-zA-Z ]+$"
                                                                                  oninvalid="this.setCustomValidity('Il campo evidenziato deve avere solo lettere e/o spazi al suo interno')"
                                                                                  onchange="this.setCustomValidity('')">
                </div>
                <div class="mb-3"><label class="form-label">Vecchia Password</label><input class="form-control"
                                                                                           type="password"
                                                                                           id="oldPassword" value=""
                                                                                           required=""
                                                                                           name="oldPassword"
                                                                                           pattern="^(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\w\d\s:])([^\s]){8,12}$"
                                                                                           oninvalid="this.setCustomValidity('Inserisci una password valida. La psw deve essere tra gli 8 e i 12 ' +
                             'caratteri e deve contenere almeno una lettera MAIUSCOLA, una minuscola, un numero e un carattere speciale.')"
                                                                                           onchange="this.setCustomValidity('')">
                </div>
                <div class="mb-3"><label class="form-label">Nuova Password</label><input class="form-control"
                                                                                         type="password"
                                                                                         id="newPassword" value=""
                                                                                         required=""
                                                                                         name="newPassword"
                                                                                         pattern="^(?=.*\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\w\d\s:])([^\s]){8,12}$"
                                                                                         oninvalid="this.setCustomValidity('Inserisci una password valida. La psw deve essere tra gli 8 e i 12 ' +
                             'caratteri e deve contenere almeno una lettera MAIUSCOLA, una minuscola, un numero e un carattere speciale.')"
                                                                                         onchange="this.setCustomValidity('')">
                </div>
                <div class="mb-3">
                    <div class="row">
                        <div class="col-md-6 col-lg-12 col-xl-12 align-items-center button">
                            <button id="modifyButton" class="btn btn-primary d-block w-100" type="submit">Conferma
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </section>
</main>
<footer class="page-footer"><img class="img-fluid"
                                 src="../assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
</body>
</html>
