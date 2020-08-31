package gr.publicsoft.springbootcrud.repository;

import gr.publicsoft.springbootcrud.model.Person;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:9000")
@RepositoryRestResource
public interface PersonRepository extends JpaRepository<Person, Long> {

    @ApiOperation("Finds a Person by their email")
    Person findByEmail(@ApiParam("The email to search against") String email);

    @ApiOperation("Fetches a list of active/inactive Persons")
    List<Person> findByActive(@ApiParam("Whether to fetch active users or not") boolean isActive);

    @ApiOperation("Searches for Persons by their name or email")
    @Query("SELECT p FROM Person p "
            + "WHERE p.email LIKE CONCAT('%',?1,'%') "
            + "     OR p.name LIKE CONCAT('%',?1,'%')")
    Page<Person> findByQuery(@ApiParam("The key to search against") @Param("query") String query, Pageable pageable);

    @ApiOperation("Counts active users")
    @Query("SELECT COUNT(p) FROM Person p "
            + "WHERE p.active = true "
            + "     AND p.email IS NOT NULL ")
    Long countActiveUsers();


    /*These are just for swagger descriptions*/

    @ApiOperation("Fetches all Persons")
    @Override
    Page<Person> findAll(Pageable pageable);

    @ApiOperation("Finds a Person by id")
    @Override
    Optional<Person> findById(Long id);

    @ApiOperation("Saves a Person")
    @Override
    <S extends Person> S save(S s);

    @ApiOperation("Deletes a Person by id")
    @Override
    void deleteById(Long id);
}

