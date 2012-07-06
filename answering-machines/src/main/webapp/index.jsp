<html>
<head>
    <link rel="stylesheet" href="http://jquery.bassistance.de/treeview/jquery.treeview.css"/>
    <link rel="stylesheet" href="css/ivrtree.css"/>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js">
    </script>
    <script src="http://jquery.bassistance.de/treeview/jquery.treeview.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#tree').treeview();
        })
    </script>

</head>
<body>
<h2>Answering machine</h2>


<ul id="tree" class="filetree">
    <li><span class="folder">Option 1</span>
        <ul>
            <li><span class="audio">Play Audio "Something"</span></li>
        </ul>
    </li>
</ul>
</body>
</html>
