<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html >
<html lang="en">
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <title>Title</title>
</head>
<body>

    <div id = "commentTable" border="1"></div>

    <form:form action="" method="put" id = "commentUpdateForm" style="display: none;">
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <div class='row'>
            <div id="ownerDivId" style="color: rgb(204, 0, 0); font-weight: bold; margin-top: 10px"></div>
            <div>
                <textarea id ="commentPut"  name="text" class="form-control" rows="2" cols="14"></textarea>
            </div>
        </div>
        <div style="display: flex; align-self: flex-end; justify-content: flex-end; align-items: flex-end; padding-bottom: 10px">
            <button class="btn btn-lg btn-primary btn-block" style="margin-top: 10px" type="submit">Update</button>
        </div>
    </form:form>

    <form:form action="" method="post" id = "commentAddForm" >
        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
        <div class='row'>
            <div>Comment</div>
            <div>
                <textarea id ="commentPost"  name="text" class="form-control" rows="2" cols="14"></textarea>
            </div>
        </div>
        <div style="display: flex; align-self: flex-end; justify-content: flex-end; align-items: flex-end;">
            <button class="btn btn-lg btn-primary btn-block" style="margin-top: 10px" type="submit">Add</button>
        </div>
    </form:form>

    <script type="text/javascript">
        let comments = '${comments}';
        let commentsJSON = JSON.parse(comments);
        let commentTable = document.getElementById("commentTable");

        commentsJSON.forEach(function(comment) {
            setActionForAddForm(comment.noteId);

            if (!comment.alien) {
                document.getElementById("commentUpdateForm").action = "/notes/" + comment.noteId + "/comments/" + comment.id;
                document.getElementById("commentUpdateForm").style.display = 'block';
                document.getElementById("ownerDivId").innerHTML = comment.owner;
                document.getElementById("commentPut").innerHTML = comment.text;
                document.getElementById("commentAddForm").style.display = 'none';
            } else {
                let commonDiv = document.createElement('div');

                let ownerDiv = document.createElement('div');

                let commentDiv = document.createElement('textarea');


                ownerDiv.style.color = 'rgb(204, 0, 0)';
                ownerDiv.style.fontWeight = 'bold';
                ownerDiv.innerHTML = comment.owner;

                commentDiv.readOnly = true;
                commentDiv.innerHTML = comment.text;
                commentDiv.rows = 2;
                commentDiv.cols = 14;

                commonDiv.appendChild(ownerDiv);
                commonDiv.appendChild(commentDiv);

                commentTable.appendChild(commonDiv);
            }
        });

        function setActionForAddForm(id) {
            document.getElementById("commentAddForm").action = "/notes/" + id + "/comments";
        }

    </script>
</body>
</html>
