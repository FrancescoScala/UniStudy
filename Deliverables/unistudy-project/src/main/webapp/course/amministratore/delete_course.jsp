<%--
  Created by IntelliJ IDEA.
  Member: nickm
  Date: 06/02/2023
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Rimuovi corso</title>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            viewCourses();
            $("#deleteCourseButton").click(chiamaControl);

            function viewCourses() {
                $("#courses").find('option').remove();
                $.post("<%=request.getContextPath()%>/CourseControl",
                    {
                        "action": "view",
                        "qty": "all"
                    },
                    function (data) {
                            let objects = data.objects;
                            for (let i = 0; i < objects.length; i++) {
                                let object = objects[i];
                                $("#courses").append("<option value=" + object["id"] + ">" + object["title"] + "</option>");
                            }
                    });
            }

            function chiamaControl() {
                $.post("<%=request.getContextPath()%>/CourseControl",
                    {
                        "action": $("#action").val(),
                        "id": $("#courses option:selected").val()
                    },
                    function (data) {
                        var success = "\"OK\"";
                        var result = (JSON.stringify(data.result)).localeCompare(success);

                        if (result == 0) {
                            $("#mex").html("Rimozione effettuata");
                        } else {
                            $("#mex").html(data.result);
                        }
                        viewCourses();
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
                            <li><a href="<%=request.getContextPath()%>/course/amministratore/add_course.jsp">Aggiungi corso</a></li>
                            <li><a class="active" href="<%=request.getContextPath()%>/course/amministratore/delete_course.jsp">Rimuovi corso</a></li>
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <div class="row">
                            <h3><span id="mex"></span></h3>
                            <div class="col" style="padding-left: 0px;padding-right: 0px;">
                                <form onsubmit="return false;">
                                    <input type="hidden" id="action" name="action" value="delete">
                                    <h2 style="margin-bottom: 30px;">Rimuovi corso</h2>
                                    <div class="mb-3"><label class="form-label" for="coursesSelect">Corso</label><select
                                            class="form-select h-25" id="coursesSelect">
                                        <optgroup label="Seleziona un corso" id="courses">
                                            <option value="12" selected="">Programmazione Distribuita</option>
                                            <option value="13">Analisi 1</option>
                                            <option value="14">Matematica Discreta</option>
                                        </optgroup>
                                    </select></div>
                                    <div class="mb-3">
                                        <div class="row">
                                            <div class="col-md-12 col-xl-12 justify-content-center button">
                                                <button id="deleteCourseButton" class="btn btn-primary d-block w-100"
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

