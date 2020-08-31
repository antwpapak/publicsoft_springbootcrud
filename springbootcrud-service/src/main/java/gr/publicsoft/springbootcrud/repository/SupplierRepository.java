package gr.publicsoft.springbootcrud.repository;

import gr.publicsoft.springbootcrud.model.Supplier;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:9000")
@RepositoryRestResource
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @ApiOperation("Searches for Suppliers by their company name or vat number")
    @Query("SELECT s FROM Supplier s "
            + "WHERE s.companyName LIKE CONCAT('%',?1,'%') OR s.vatNumber LIKE CONCAT('%',?1,'%')")
    Page<Supplier> findByQuery(@Param("query") String query, Pageable pageable);



    /*These are just for swagger descriptions*/

    @ApiOperation("Fetches all Suppliers")
    @Override
    Page<Supplier> findAll(Pageable pageable);

    @ApiOperation("Finds a Supplier by id")
    @Override
    Optional<Supplier> findById(Long id);

    @ApiOperation("Saves a Supplier")
    @Override
    <S extends Supplier> S save(S s);

    @ApiOperation("Deletes a Supplier")
    @Override
    void delete(Supplier supplier);
}
