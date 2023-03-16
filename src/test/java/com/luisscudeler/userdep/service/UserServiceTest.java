package com.luisscudeler.userdep.service;


import com.luisscudeler.userdep.entities.Department;
import com.luisscudeler.userdep.entities.User;
import com.luisscudeler.userdep.repository.UserRepository;
import com.luisscudeler.userdep.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "Maria";
    private static final String EMAIL = "maria@gmail.com";
    private static final Department DEPARTMENT = new Department(2L,"Informatica");



    @InjectMocks //classe que estamos tratando
    private UserService service;

    @Mock //só quero simular um comportamento do banco de dados e nao acessa-lo realmente
    private UserRepository repository;

    private User user;
    private User user1;
    private User user2;

    private User updatedUser;

    private Optional<User> optionalUser;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }



    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        Mockito.when(repository.findAll()).thenReturn(List.of(user));
        List<User> response = service.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(User.class, response.get(0).getClass());
        Assertions.assertEquals(ID, response.get(0).getId());
        Assertions.assertEquals(NAME, response.get(0).getName());
        Assertions.assertEquals(EMAIL, response.get(0).getEmail());
        Assertions.assertEquals(DEPARTMENT, response.get(0).getDepartment());
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(optionalUser);

        User response = service.findById(ID);

        Assertions.assertEquals(User.class, response.getClass()); //verifica se a classe de retorno é a que eu espero
        Assertions.assertNotNull(response); //se retorna nulo
        Assertions.assertEquals(ID, response.getId()); //se retorna o certo
    }

    @Test
    void whenCreateThenReturnSucess() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        User response = service.insert(user);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID,response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(DEPARTMENT, response.getDepartment());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        Mockito.when(repository.findById(Mockito.anyLong())).thenThrow(new ResourceNotFoundException(ID)); //aqui estamos mocando

        try{
            service.findById(ID);
        } catch (Exception ex){
            Assertions.assertEquals(ResourceNotFoundException.class, ex.getClass());
            Assertions.assertEquals(String.format("Reource not Found. Id %s",ID), ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(optionalUser);
        Mockito.doNothing().when(repository).deleteById(Mockito.anyLong());
        service.delete(ID);

        Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    void whenUpdateThenReturnSucess() {
        Mockito.when(repository.getReferenceById(1L)).thenReturn(user);
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        User returnedUser = service.update(1L, updatedUser);

        Mockito.verify(repository, Mockito.times(1)).getReferenceById(1L);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));

        Assertions.assertEquals(updatedUser, returnedUser);
    }

    @Test
    public void testUpdateNonexistentUser() {
        Mockito.when(repository.getReferenceById(1L)).thenThrow(new ResourceNotFoundException(ID));

        try{
            service.update(1L, updatedUser);
        } catch (Exception ex){
            Assertions.assertEquals(ResourceNotFoundException.class, ex.getClass());
            Assertions.assertEquals(String.format("Reource not Found. Id %s",ID), ex.getMessage());
        }


    }

    @Test
    void whenUpdateDateThenReturnStockUpdated() {
        // Cria duas instâncias de Stock
        //criado no cabeçalho

        user1.setName("Tutu");

        // Chama o método updateDate
        service.updateData(user2, user1);

        // Verifica se a lista de stock2 foi atualizada corretamente
        Assertions.assertEquals("Tutu", user2.getName());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, DEPARTMENT);
        updatedUser = new User(2L,"Bob","bob@gmail.com",new Department(3L, "Varejo"));
        optionalUser = Optional.of(user);
        user1 = new User();
        user2 = new User();

    }

}