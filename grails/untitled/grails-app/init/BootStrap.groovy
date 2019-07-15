import untitled.Category
import untitled.Item
import untitled.Site
import untitled.User

import java.time.DateTimeException
import java.time.LocalDate

class BootStrap {
    def Date date = new Date();

    def init = { servletContext ->
        new User(dateFrom: date, dateTo:date,totalVisits: 0,visitDetail: "-").save()


        def site = new Site(name: "Mercado Libre Argentina").save()

        def cat1 = new Category(name: "Bebidas", site: site).save()
        new Category(name: "Alimentos", site: site).save()
        new Category(name: "Calzado", site: site).save()
        new Category(name: "Electrodomesticos", site: site).save()

        def lista = []
        lista << cat1

        new Item(title: "Coca cola", subtitle: "Zero", categoty: lista, imageUrl: "/images1").save()
        new Item(title: "Coca sprite", subtitle: "Zero", categoty: lista, imageUrl: "/images2").save()
        new Item(title: "Coca fanta", subtitle: "Zero", categoty: lista, imageUrl: "/images3").save()

    }
    def destroy = {
    }
}
