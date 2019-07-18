package controllers

import (
	"../domains"
	"../services"
	"../utils/apierrors"
	"github.com/gin-gonic/gin"
	"net/http"
	"strconv"
	"sync"
)
const (
	userID = "userID"
)
func GetResult(c *gin.Context){
	var wg sync.WaitGroup
	userID := c.Param(userID)
	channel := make(chan *domains.Result)
	channelError := make(chan *apierrors.ApiError)

	var site domains.Site
	var country domains.Country

	defer close(channel)
	defer close(channelError)

	go channelFunc(channel, &site, &country, &wg)
	go channelErrorFunc (channelError, channel)




	id, err := strconv.ParseInt(userID,10,64)
	if err != nil {
		apiError := &apierrors.ApiError{
			Message:err.Error(),
			Status: http.StatusBadRequest,
		}
		c.JSON(apiError.Status, apiError)
		return
	}

	resultUser, error := services.GetUser(id)

	if error != nil {
		if error != nil {
			c.JSON(error.Status, error)
			return
		}
	}


	wg.Add(1)
	go services.GetCountry(resultUser.CountryID, channel,channelError)


	wg.Add(1)
	go services.GetSite(resultUser.SiteID, channel,channelError)



	wg.Wait()
	c.JSON(http.StatusOK,services.GetResult(resultUser,&country,&site))
}
func channelFunc(channel chan *domains.Result, site *domains.Site, country *domains.Country, wg *sync.WaitGroup) {
	for i :=0; i<2; i++  {
		var p  = <- channel
		if p != nil{
			if p.Site != nil{
				site = p.Site
			}
			if p.Country != nil{
				country = p.Country
			}

			wg.Done()
		}
	}
}
func channelErrorFunc(channelError chan *apierrors.ApiError, channel chan *domains.Result) {
	for i :=0; i<2; i++  {
		i := <- channelError
		if i != nil{
			close(channel)
			<- channel

		}
	}
}
