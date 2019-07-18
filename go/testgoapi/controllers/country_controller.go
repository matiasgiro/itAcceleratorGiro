package controllers

import (
	"../services"
	"github.com/gin-gonic/gin"
	"net/http"
)
const (
	paramCountryID = "countryID"
)
func GetCountry(c *gin.Context){
	countryID := c.Param(paramCountryID)

	user, apiError := services.GetCountryFromAPI(countryID)
	if apiError != nil {
		c.JSON(apiError.Status, apiError)
		return
	}
	 c.JSON(http.StatusOK,user)
}