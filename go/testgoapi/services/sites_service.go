package services

import (
	"../domains"
	"../utils/apierrors"
)

func GetSitesFromAPI()([]domains.Site) {

	sites, _ := domains.GetAllSites()

	return sites
}
func GetSiteFromAPI(siteID string) (*domains.Site, *apierrors.ApiError ){
	site := &domains.Site{
		ID:siteID,
	}
	if err := site.Get(); err != nil{
		return nil, err
	}
	return site,nil
}

func GetSite(siteID string, ch chan *domains.Result, chError chan *apierrors.ApiError ) (){
	site := &domains.Site{
		ID:siteID,
	}
	result := domains.Result{}
	if err := site.Get(); err != nil{
		chError <- err
	}
	result.Site = site
	ch <- &result
}
