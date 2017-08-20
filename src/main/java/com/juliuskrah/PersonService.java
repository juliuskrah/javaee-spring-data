package com.juliuskrah;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
@Path("/v1.0/persons")
public class PersonService {
    private static final Logger LOGGER = Logger.getLogger(PersonService.class.getName());
    @PersistenceContext
    private EntityManager entityManager;
    private PersonRepository personRepository;

    @PostConstruct
    private void init() {
        RepositoryFactorySupport factory = new JpaRepositoryFactory(entityManager);
        this.personRepository = factory.getRepository(PersonRepository.class);
    }

    /**
     * POST /api/v1.0/persons : Create a new person.
     *
     * @param person the person to create
     * @return the Response with status 201 (Created) and with location of newly created resource
     * or with status 400 (Bad Request) if the person has
     * already an ID
     */
    @POST
    public Response createPerson(Person person) {
        LOGGER.log(Level.INFO, "REST request to create Person : {0}", person);
        if (Objects.nonNull(person.getId()))
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        person.setCreatedDate(LocalDateTime.now());
        this.personRepository.save(person);

        return Response.created(URI.create("/v1.0/persons/" + person.getId())).build();
    }

    /**
     * GET /api/v1.0/persons : get all the people.
     *
     * @return the list of people with status 200 (OK)
     */
    @GET
    public List<Person> findPeople() {
        LOGGER.log(Level.INFO, "REST request to fetch all Persons");
        return this.personRepository.findAll();
    }


    /**
     * GET /api/v1.0/persons/:id : get the "id" person.
     *
     * @param id the id of the person to retrieve
     * @return the Response with status 200 (OK) and with body the person,
     * or with status 404 (Not Found)
     */
    @GET
    @Path("{id: [0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}}")
    public Response findPerson(@PathParam("id") String id) {
        LOGGER.log(Level.INFO, "REST request to fetch Person : {0}", id);
        Person person = this.personRepository.findOne(id);
        if (Objects.isNull(person))
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return Response.ok(person).build();
    }

    /**
     * PUT /api/v1.0/persons/:id : Updates an existing person.
     *
     * @param id     the id of the person to retrieve
     * @param person the person to update
     * @return the Response with status 204 (NO CONTENT) and with no body,
     * or with status 400 (Bad Request) if the person is not
     * valid, or with status 500 (Internal Server Error) if the person
     * couldn't be updated
     */
    @PUT
    @Path("{id: [0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}}")
    public Response updatePerson(@PathParam("id") String id, Person person) {
        LOGGER.log(Level.INFO, "REST request to update Person : {0}", person);
        Person person1 = this.personRepository.findOne(id);
        if (Objects.isNull(person1))
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        person.setId(id);
        person.setCreatedDate(person1.getCreatedDate());
        person.setModifiedDate(LocalDateTime.now());
        this.personRepository.save(person);
        return Response.noContent().build();
    }

    /**
     * DELETE  /api/v1.0/persons/:id : delete the "id" person.
     *
     * @param id the id of the person to delete
     * @return the Response with status 204 (NO CONTENT)
     */
    @DELETE
    @Path("{id: [0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}}")
    public Response deletePerson(@PathParam("id") String id) {
        LOGGER.log(Level.INFO, "REST request to delete Person : {0}", id);
        this.personRepository.delete(id);
        return Response.noContent().build();
    }
}
