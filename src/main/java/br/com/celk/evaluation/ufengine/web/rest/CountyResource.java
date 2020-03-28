package br.com.celk.evaluation.ufengine.web.rest;

import br.com.celk.evaluation.ufengine.domain.County;
import br.com.celk.evaluation.ufengine.repository.CountyRepository;
import br.com.celk.evaluation.ufengine.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.celk.evaluation.ufengine.domain.County}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CountyResource {

    private final Logger log = LoggerFactory.getLogger(CountyResource.class);

    private static final String ENTITY_NAME = "county";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CountyRepository countyRepository;

    public CountyResource(CountyRepository countyRepository) {
        this.countyRepository = countyRepository;
    }

    /**
     * {@code POST  /counties} : Create a new county.
     *
     * @param county the county to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new county, or with status {@code 400 (Bad Request)} if the county has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/counties")
    public ResponseEntity<County> createCounty(@Valid @RequestBody County county) throws URISyntaxException {
        log.debug("REST request to save County : {}", county);
        if (county.getId() != null) {
            throw new BadRequestAlertException("A new county cannot already have an ID", ENTITY_NAME, "idexists");
        }
        County result = countyRepository.save(county);
        return ResponseEntity.created(new URI("/api/counties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /counties} : Updates an existing county.
     *
     * @param county the county to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated county,
     * or with status {@code 400 (Bad Request)} if the county is not valid,
     * or with status {@code 500 (Internal Server Error)} if the county couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/counties")
    public ResponseEntity<County> updateCounty(@Valid @RequestBody County county) throws URISyntaxException {
        log.debug("REST request to update County : {}", county);
        if (county.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        County result = countyRepository.save(county);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, county.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /counties} : get all the counties.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of counties in body.
     */
    @GetMapping("/counties")
    public ResponseEntity<List<County>> getAllCounties(Pageable pageable) {
        log.debug("REST request to get a page of Counties");
        Page<County> page = countyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /counties/:id} : get the "id" county.
     *
     * @param id the id of the county to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the county, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/counties/{id}")
    public ResponseEntity<County> getCounty(@PathVariable Long id) {
        log.debug("REST request to get County : {}", id);
        Optional<County> county = countyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(county);
    }

    /**
     * {@code DELETE  /counties/:id} : delete the "id" county.
     *
     * @param id the id of the county to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/counties/{id}")
    public ResponseEntity<Void> deleteCounty(@PathVariable Long id) {
        log.debug("REST request to delete County : {}", id);
        countyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
