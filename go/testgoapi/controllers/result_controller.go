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
var errG bool = false;
func GetResult(c *gin.Context){
	var wg sync.WaitGroup
	userID := c.Param(userID)
	channel := make(chan *domains.Result)
	channelError := make(chan *apierrors.ApiError)

	var site domains.Site = domains.Site{}
	var country domains.Country = domains.Country{}


	var errorConsulta apierrors.ApiError

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


	go channelFunc(channel, &site, &country, &wg)
	go channelErrorFunc (channelError, channel, c,&errorConsulta)


	wg.Add(1)
	go services.GetCountry(resultUser.CountryID, channel,channelError)


	wg.Add(1)
	go services.GetSite(resultUser.SiteID, channel,channelError)



	wg.Wait()
	if errG{

	}else{
		c.JSON(http.StatusOK,services.GetResult(resultUser,&country,&site))
	}
}
func channelFunc(channel chan *domains.Result, site *domains.Site, country *domains.Country, wg *sync.WaitGroup) {
	for i :=0; i<2; i++  {
		p  := <- channel

		if p != nil{
			if p.Site != nil{
				*site = *p.Site
			}
			if p.Country != nil{
				*country = *p.Country
			}
		}
		wg.Done()
	}
}
func channelErrorFunc(channelError chan *apierrors.ApiError, channel chan *domains.Result, c *gin.Context, err *apierrors.ApiError) {

	for i :=0; i<2; i++  {
		i := <- channelError
		if i != nil {
			errG = true;
			err = i;
			c.JSON(i.Status,i)
			return
		}
	}
}
