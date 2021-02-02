var app = angular.module('demo', []);
app.controller('Tree', function($scope, $http) {
  $http.get('http://localhost:8080/tree').
  then(function(response) {
    $scope.tree = response.data;
    $scope.values = [];

    canvas = document.getElementById("myCanvas");
    var ctx = canvas.getContext("2d");

    for (i = 0; i < $scope.tree.length; i++) {
      // add line
      ctx.beginPath();
      ctx.moveTo($scope.tree[i].coordX * 100 + 100, $scope.tree[i].coordY * 100 + 100);
      ctx.lineTo($scope.tree[i].parentCoordX * 100 + 100, $scope.tree[i].parentCoordY * 100 + 100);
      ctx.stroke();
    }

    for (i = 0; i < $scope.tree.length; i++) {
      //add circle
      ctx.beginPath();
      ctx.arc($scope.tree[i].coordX * 100 + 100, $scope.tree[i].coordY * 100 + 100, 25, 0, 2 * Math.PI);
      ctx.fillStyle = 'white';
      ctx.fill();
      ctx.stroke();
      //add number to circle
      ctx.font = "20px Georgia";
      ctx.fillStyle = 'black'
      ctx.textAlign = 'center';
      ctx.textBaseline = 'middle';
      ctx.fillText($scope.tree[i].value, $scope.tree[i].coordX * 100 + 100, $scope.tree[i].coordY * 100 + 100);
    }
  });
});