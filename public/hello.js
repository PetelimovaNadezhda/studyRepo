var app = angular.module('demo', []);
app.controller('Tree', function($scope, $http) {
  $http.get('http://localhost:8080/tree').
  then(function(response) {
    $scope.tree = response.data;
    $scope.values = [];

    canvas = document.getElementById("myCanvas");

    for (i = 0; i < $scope.tree.length; i++) {
      //add circle
      var ctx = canvas.getContext("2d");
      ctx.beginPath();
      if ($scope.tree[i].side == "LEFT") {
        ctx.arc(canvas.width / 2 - i * 100, 75 + $scope.tree[i].level * 100, 25, 0, 2 * Math.PI);
      }
      else {
        ctx.arc(canvas.width / 2 + i * 100, 75 + $scope.tree[i].level * 100, 25, 0, 2 * Math.PI);
      }
      ctx.stroke();
      //add number to circle
      var ctx = canvas.getContext("2d");
      ctx.font = "20px Georgia";
      if ($scope.tree[i].side == "LEFT") {
        ctx.fillText($scope.tree[i].value, canvas.width / 2 - i * 100, 75 + $scope.tree[i].level * 100);
      }
      else {
        ctx.fillText($scope.tree[i].value, canvas.width / 2 + i * 100, 75 + $scope.tree[i].level * 100);
      }
    }
  });
});