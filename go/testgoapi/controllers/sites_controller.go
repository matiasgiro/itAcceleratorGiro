package controllers

import (
	"../services"
	"github.com/gin-gonic/gin"
	"net/http"
)
const (
	paramSiteID = "siteID"
)
func GetAllSites(c *gin.Context){

	c.JSON(http.StatusOK,services.GetSitesFromAPI())

}
func GetSite(c *gin.Context){
	siteID := c.Param(paramSiteID)
	user, apiError := services.GetSiteFromAPI(siteID)
	if apiError != nil {
		c.JSON(apiError.Status, apiError)
		return
	}
	c.JSON(http.StatusOK,user)
}