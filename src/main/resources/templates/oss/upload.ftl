<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net. nz/web/thymeleaf/layout"
      layout:decorate="layout">
<head>
    <meta charset="utf-8"/>
    <title>文件上传</title>
</head>
<body>
<form method="post" enctype="multipart/form-data" action="/company/readExcel">
    <p>文件：<input type="text" name="companyClassifyId"/></p>
    <p>文件：<input type="file" name="excel"/></p>
    <p><input type="submit" value="上传" /></p>
</form>
</body>