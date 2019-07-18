package domains

import (
	"../utils/apierrors"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
)

type Site struct {
	ID									string	`json:"id"`
	Name								string	`json:"name"`
	CountryID							string	`json:"country_id"`
	SaleFeesMode						string	`json:"sale_fees_mode"`
	MercadopagoVersion					int	`json:"mercadopago_version"`
	DefaultCurrencyId					string	`json:"default_currency_id"`
	ImmediatePayment					string	`json:"immediate_payment"`
	PaymentMethodIds					[]string	`json:"payment_method_ids"`

}



const urlSites = "https://api.mercadolibre.com/sites/"

func GetAllSites() ([]Site,*apierrors.ApiError){

	var site []Site

	url := fmt.Sprintf("%s",urlSites)
	res, err := http.Get(url)
	if err != nil{
		return nil,&apierrors.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}
	data, err := ioutil.ReadAll(res.Body)
	if err != nil{
		return nil,&apierrors.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}
	if err = json.Unmarshal(data, &site); err != nil {
		return nil,&apierrors.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}
	return site,nil
}

func (site *Site) Get() *apierrors.ApiError{

	if site.ID == "" {
		return &apierrors.ApiError{
			Message: "El id de usuario es 0",
			Status: http.StatusBadRequest,
		}
	}

	url := fmt.Sprintf("%s%s", urlSites, site.ID)

	response, err := http.Get(url)
	if err != nil{
		return &apierrors.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}

	data, err := ioutil.ReadAll(response.Body)
	if err != nil {
		return &apierrors.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}

	if err := json.Unmarshal(data, &site); err != nil{
		return &apierrors.ApiError{
			Message: err.Error(),
			Status: http.StatusInternalServerError,
		}
	}

	return nil
}