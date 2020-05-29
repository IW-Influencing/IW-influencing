Feature: csrf and log-out endpoint

Background:
* Given url baseUrl
* Given path '/perfil'
* call read('login1.feature')
* def util = Java.type('karate.KarateTests')

Scenario: user page
    Given param idUsuario = 1
    When method GET
    Then status 200
    * string response = response
    * def userName = util.selectHtml(response, "h2>span")
    And match response contains 'Nombre y apellidos</h2> <span>a Abundio Ejemplez'
