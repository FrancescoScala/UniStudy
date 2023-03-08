<%--
  Created by IntelliJ IDEA.
  Member: francesco
  Date: 10/02/23
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Pubblica appunto</title>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            console.log("Ready!");
            $("#createNoteButton").click(chiamaControl);

            function chiamaControl() {
                console.log("chiamaControl");
                $.post("<%=request.getContextPath()%>/NoteControl",
                    {
                        "action": "add",
                        "id": $("#id").val(),
                        "title": $("#title").val(),
                        "description": $("#description").val()
                    },
                    function (data) {
                        var success = "\"OK\"";
                        var result = (JSON.stringify(data.result)).localeCompare(success);

                        if (result == 0) {
                            console.log("ok!");
                            $("#mex").html("Aggiunta appunto effettuata");
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
                <h2>PUBBLICA APPUNTO</h2>
            </div>
            <h3 id="mex"></h3>
            <form onsubmit="return false;">
                <input type="hidden" id="id" name="id" value="<%=request.getParameter("id")%>">
                <div class="mb-3"><label class="form-label" for="title">Titolo</label><input class="form-control"
                                                                                             name="title" required=""
                                                                                             pattern="^.{1,50}$"
                                                                                             oninvalid="this.setCustomValidity('Il campo evidenziato deve avere da 1 a 50 caratteri')"
                                                                                             onchange="this.setCustomValidity('')"
                                                                                             type="text"
                                                                                             id="title"></div>
                <div class="mb-3"><label class="form-label" for="description">Descrizione</label><textarea
                        class="form-control" name="description" required=""
                        pattern="^.{1,300}$"
                        oninvalid="this.setCustomValidity('Il campo evidenziato deve avere da 1 a 300 caratteri')"
                        onchange="this.setCustomValidity('')" id="description"></textarea></div>
                <div class="mb-3">
                    <div class="row">
                        <div class="col-md-6 col-lg-12 col-xl-12 align-items-center button">
                            <button id="createNoteButton" class="btn btn-primary d-block w-100" type="submit">Conferma
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </section>
</main>
<footer class="page-footer"><img class="img-fluid"
                                 src="../../assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
</body>
</html>