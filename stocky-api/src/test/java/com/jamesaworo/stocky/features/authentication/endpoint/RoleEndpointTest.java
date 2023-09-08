package com.jamesaworo.stocky.features.authentication.endpoint;

import com.jamesaworo.stocky.features.authentication.data.interactor.contract.IRoleInteractor;
import com.jamesaworo.stocky.features.authentication.data.request.PermissionRequest;
import com.jamesaworo.stocky.features.authentication.data.request.RoleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleEndpointTest {

    @Mock
    private IRoleInteractor interactor;

    @InjectMocks
    private RoleEndpoint underTest;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    }

    // create method
    @Test
    @DisplayName("Given a valid role request, when creating a role, then should return ResponseEntity with success")
    public void create_ValidRoleRequest_ReturnsResponseEntityWithSuccess() {
        // Given
        RoleRequest roleRequest = new RoleRequest();

        // When
        when(interactor.create(roleRequest)).thenReturn(ResponseEntity.ok(roleRequest));
        ResponseEntity<RoleRequest> response = underTest.create(roleRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(roleRequest);

        // Verify that the interactor's create method was called with the correct role request
        verify(interactor).create(roleRequest);
    }

    @Test
    @DisplayName("Given an invalid role request, when creating a role, then should return ResponseEntity with error")
    public void create_InvalidRoleRequest_ReturnsResponseEntityWithError() {
        // Given
        RoleRequest roleRequest = new RoleRequest();
        // Set up an invalid role request that fails validation or contains incorrect data

        // When
        ResponseEntity<RoleRequest> response = underTest.create(roleRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        // Add more assertions to validate the error response, such as error message or error code

        // Verify that the interactor's create method was not called
        verify(interactor, never()).create(roleRequest);
    }

    // getAll method
    @Test
    @DisplayName("When getting all roles, then should return ResponseEntity with success")
    public void getAll_ReturnsResponseEntityWithSuccess() {
        // Given
        List<RoleRequest> expectedRoles = List.of(
                new RoleRequest(),
                new RoleRequest()
        );
        // Set up the expected result from the interactor

        when(interactor.getAll()).thenReturn(ResponseEntity.ok(expectedRoles));

        // When
        ResponseEntity<List<RoleRequest>> response = underTest.getAll();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedRoles);

        // Verify that the interactor's getAll method was called
        verify(interactor).getAll();
    }

    @Test
    @DisplayName("When getting all roles encounters an error, then should return ResponseEntity with error")
    public void getAll_ReturnsResponseEntityWithError() {
        // Given
        when(interactor.getAll()).thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

        // When
        ResponseEntity<List<RoleRequest>> response = underTest.getAll();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        // Verify that the interactor's getAll method was called
        verify(interactor).getAll();
    }

    // getOne method
    @Test
    @DisplayName("Given a valid role ID, when getting a role, then should return ResponseEntity with success")
    public void getOne_ValidRoleId_ReturnsResponseEntityWithSuccess() {
        // Given
        Long roleId = 1L;
        RoleRequest expectedRole = new RoleRequest();
        // Set up the expected result from the interactor

        when(interactor.getOne(roleId)).thenReturn(ResponseEntity.ok(Optional.of(expectedRole)));

        // When
        ResponseEntity<Optional<RoleRequest>> response = underTest.getOne(roleId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Optional.of(expectedRole));

        // Verify that the interactor's getOne method was called with the correct role ID
        verify(interactor).getOne(roleId);
    }

    @Test
    @DisplayName("Given an invalid role ID, when getting a role, then should return ResponseEntity with error")
    public void getOne_InvalidRoleId_ReturnsResponseEntityWithError() {
        // Given
        Long roleId = 1L;
        when(interactor.getOne(roleId)).thenReturn(ResponseEntity.notFound().build());

        // When
        ResponseEntity<Optional<RoleRequest>> response = underTest.getOne(roleId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        // Verify that the interactor's getOne method was called with the correct role ID
        verify(interactor).getOne(roleId);
    }

    // getRolePermissions method
    @Test
    @DisplayName("Given a valid role ID, when getting role permissions, then should return ResponseEntity with success")
    public void getRolePermissions_ValidRoleId_ReturnsResponseEntityWithSuccess() {
        // Given
        Long roleId = 1L;
        List<PermissionRequest> expectedPermissions = List.of();
        // Set up the expected result from the interactor

        when(interactor.getRolePermissions(roleId)).thenReturn(ResponseEntity.ok(expectedPermissions));

        // When
        ResponseEntity<List<PermissionRequest>> response = underTest.getRolePermissions(roleId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedPermissions);

        // Verify that the interactor's getRolePermissions method was called with the correct role ID
        verify(interactor).getRolePermissions(roleId);
    }

    @Test
    @DisplayName("Given an invalid role ID, when getting role permissions, then should return ResponseEntity with error")
    public void getRolePermissions_InvalidRoleId_ReturnsResponseEntityWithError() {
        // Given
        Long roleId = 1L;
        when(interactor.getRolePermissions(roleId)).thenReturn(ResponseEntity.notFound().build());

        // When
        ResponseEntity<List<PermissionRequest>> response = underTest.getRolePermissions(roleId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        // Verify that the interactor's getRolePermissions method was called with the correct role ID
        verify(interactor).getRolePermissions(roleId);
    }

    // update method
    @Test
    @DisplayName("Given a valid role request, when updating a role, then should return ResponseEntity with success")
    public void update_ValidRoleRequest_ReturnsResponseEntityWithSuccess() {
        // Given
        RoleRequest roleRequest = new RoleRequest();
        // Set up any necessary test data for the role request

        when(interactor.update(roleRequest)).thenReturn(ResponseEntity.ok(Optional.of(roleRequest)));

        // When
        ResponseEntity<Optional<RoleRequest>> response = underTest.update(roleRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Optional.of(roleRequest));

        // Verify that the interactor's update method was called with the correct role request
        verify(interactor).update(roleRequest);
    }

    @Test
    @DisplayName("Given an invalid role request, when updating a role, then should return ResponseEntity with error")
    public void update_InvalidRoleRequest_ReturnsResponseEntityWithError() {
        // Given
        RoleRequest roleRequest = new RoleRequest();
        // Set up an invalid role request that fails validation or contains incorrect data

        // When
        ResponseEntity<Optional<RoleRequest>> response = underTest.update(roleRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        // Add more assertions to validate the error response, such as error message or error code

        // Verify that the interactor's update method was not called
        verify(interactor, never()).update(roleRequest);
    }

    // updateActiveStatus method
    @Test
    @DisplayName("Given a valid role ID, when updating active status, then should return ResponseEntity with success")
    public void updateActiveStatus_ValidRoleId_ReturnsResponseEntityWithSuccess() {
        // Given
        Long roleId = 1L;
        Boolean expectedStatus = true;
        // Set up the expected result from the interactor

        when(interactor.updateActiveStatus(roleId)).thenReturn(ResponseEntity.ok(Optional.of(expectedStatus)));

        // When
        ResponseEntity<Optional<Boolean>> response = underTest.updateActiveStatus(roleId);
    }

    @Test
    @DisplayName("Given a role ID, when updating the active status, then should return ResponseEntity with success")
    public void updateActiveStatus_WithValidRoleId_ReturnsResponseEntityWithSuccess() {
        // Given
        long roleId = 123L;
        // Set up any necessary test data or mocks

        when(interactor.updateActiveStatus(roleId)).thenReturn(ResponseEntity.ok(Optional.of(true)));

        // When
        ResponseEntity<Optional<Boolean>> response = underTest.updateActiveStatus(roleId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isPresent().contains(true);

        // Verify that the interactor's updateActiveStatus method was called with the correct role ID
        verify(interactor).updateActiveStatus(roleId);
    }

    @Test
    @DisplayName("Given a role ID, when updating the active status, then should return ResponseEntity with error")
    public void updateActiveStatus_WithInvalidRoleId_ReturnsResponseEntityWithError() {
        // Given
        long roleId = 456L;
        // Set up any necessary test data or mocks

        when(interactor.updateActiveStatus(roleId)).thenReturn(ResponseEntity.ok(Optional.empty()));

        // When
        ResponseEntity<Optional<Boolean>> response = underTest.updateActiveStatus(roleId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEmpty();

        // Verify that the interactor's updateActiveStatus method was called with the correct role ID
        verify(interactor).updateActiveStatus(roleId);
    }


}