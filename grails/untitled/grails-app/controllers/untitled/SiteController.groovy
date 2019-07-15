package untitled

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SiteController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Site.list(params), model:[siteCount: Site.count()]
    }

    def show(Site site) {
        respond site
    }

    def create() {
        respond new Site(params)
    }

    @Transactional
    def save(Site site) {
        if (site == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (site.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond site.errors, view:'create'
            return
        }

        site.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'site.label', default: 'Site'), site.id])
                redirect site
            }
            '*' { respond site, [status: CREATED] }
        }
    }

    def edit(Site site) {
        respond site
    }

    @Transactional
    def update(Site site) {
        if (site == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (site.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond site.errors, view:'edit'
            return
        }

        site.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'site.label', default: 'Site'), site.id])
                redirect site
            }
            '*'{ respond site, [status: OK] }
        }
    }

    @Transactional
    def delete(Site site) {

        if (site == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        site.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'site.label', default: 'Site'), site.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'site.label', default: 'Site'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
