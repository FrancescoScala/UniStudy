<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Pubblica avviso</title>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#createNoticeButton").click(chiamaControl);

            function chiamaControl() {
                $.post("<%=request.getContextPath()%>/NoticeControl",
                    {
                        "action": $("#action").val(),
                        "id": $("#id").val(),
                        "title": $("#title").val(),
                        "description": $("#description").val()
                    },
                    function (data) {
                        var success = "\"OK\"";
                        var result = (JSON.stringify(data.result)).localeCompare(success);

                        if (result == 0) {
                            $("#mex").html("Aggiunta avviso effettuata");
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
        <section class="portfolio-block projects-with-sidebar" style="padding-top: 0px;">
            <div class="container">
                <div class="heading"></div>
                <div class="row">
                    <div class="col-md-3">
                        <ul class="list-unstyled fs-5 sidebar">
                            <%String contextPath = request.getContextPath() + "/course/gestorecorso/";%>
                            <li><a href="<%=contextPath%>info-modify-course.jsp?id=<%=request.getParameter("id")%>">Modifica
                                info
                                corso</a></li>
                            <li><a class="active"
                                   href="<%=contextPath%>publish-alert.jsp?id=<%=request.getParameter("id")%>">Pubblica
                                avviso</a></li>
                            <li>
                                <a href="<%=request.getContextPath()%>/NoticeControl?action=view&id=<%=request.getParameter("id")%>">Rimuovi
                                    avviso</a></li>
                            <li><a href="<%=request.getContextPath()%>/NoteControl?action=view&id=<%=request.getParameter("id")%>">Rimuovi
                                appunto</a></li>
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <div class="row">
                            <div class="col" style="padding-left: 0px;padding-right: 0px;">
                                <form onsubmit="return false;">
                                    <input type="hidden" name="action" id="action" value="add">
                                    <input type="hidden" name="id" id="id" value="<%=request.getParameter("id")%>">
                                    <h2 style="margin-bottom: 30px;">PUBBLICA AVVISO</h2>
                                    <h3 id="mex"></h3>
                                    <div class="mb-3"><label class="form-label" for="title">Titolo</label><input
                                            class="form-control" type="text" name="title" required=""
                                            pattern="^.{1,50}$"
                                            oninvalid="this.setCustomValidity('Il campo evidenziato deve avere da 1 a 50 caratteri')"
                                            onchange="this.setCustomValidity('')" id="title"></div>
                                    <div class="mb-3"><label class="form-label"
                                                             for="description">Descrizione</label><textarea
                                            class="form-control" name="description" required=""
                                            pattern="^.{1,300}$"
                                            oninvalid="this.setCustomValidity('Il campo evidenziato deve avere da 1 a 300 caratteri')"
                                            onchange="this.setCustomValidity('')" id="description"></textarea></div>
                                    <div class="mb-3">
                                        <div class="row">
                                            <div class="col-md-12 col-xl-12 justify-content-center button">
                                                <button id="createNoticeButton" class="btn btn-primary d-block w-100"
                                                        type="submit">Conferma
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </section>
</main>
<footer class="page-footer"><img class="img-fluid"
                                 src="../../assets/img/Elegant%20Education%20Technology%20Logo%20Template%20(1).png"
                                 width="300" height="150"></footer>
</body>
</html>