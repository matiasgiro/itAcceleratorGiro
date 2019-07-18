package main

import (
	"./controllers"
	"github.com/gin-gonic/gin"
)

const(
	port = ":8080"
)

var(
	router = gin.Default()
)

func main()  {
	router.GET("/miapi/:userID", controllers.GetUser)
	router.GET("/sites", controllers.GetAllSites)
	router.GET("/country/:countryID", controllers.GetCountry)
	router.GET("/sites/:siteID", controllers.GetSite)
	router.GET("/user/:userID", controllers.GetResult)

	router.Run(port)
}
