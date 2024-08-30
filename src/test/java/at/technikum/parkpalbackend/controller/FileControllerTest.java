//package at.technikum.parkpalbackend.controller;

//@WebMvcTest(FileUploadService.class)
//@AutoConfigureMockMvc(addFilters = false)
//public class FileControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private FileService fileService;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private FileMapper fileMapper;
//
//    @MockBean
//    private JwtDecoder jwtDecoder;
//
//    @MockBean
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Autowired
//    private ObjectMapper objectMapper;

//    @Test
//    public void testCreatePicture() throws Exception {
//        // Arrange
//        FileDto fileDto = TestFixtures.testFileDto;
//        fileDto.setId("1");
//
//        File createdFile = TestFixtures.testFileTypeFile;
//        createdFile.setId("1");
//
//        when(fileMapper.toEntity(any(FileDto.class))).thenReturn(createdFile);
//        when(fileService.save(any(File.class))).thenReturn(createdFile);
//        when(fileMapper.toDto(any(File.class))).thenReturn(fileDto);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.post("/pictures")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(fileDto)))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value("1")); // Adjust as per your DTO structure
//    }

//    @Test
//    public void testGetAllPictures() throws Exception {
//        // Arrange
//        List<File> mockFiles = Collections.singletonList(File.builder().build());
//        List<FileDto> mockFileDtos = Collections.singletonList(TestFixtures.testFileDto);
//
//        when(fileService.findAllPictures()).thenReturn(mockFiles);
//        when(fileMapper.toDto(any(File.class))).thenReturn(TestFixtures.testFileDto);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.get("/pictures")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].id").value(TestFixtures.testFileDto.getId()));
//    }

//    @Test
//    public void testGetPictureByPictureId() throws Exception {
//        // Arrange
//        String pictureId = "1";
//        FileDto fileDto = TestFixtures.testFileDto;
//        fileDto.setId(pictureId);
//
//        when(fileService.findPictureByPictureId(ArgumentMatchers.eq(pictureId))).thenReturn(TestFixtures.testFileTypeFile);
//        when(fileMapper.toDto(any(File.class))).thenReturn(fileDto);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.get("/pictures/{pictureId}", pictureId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(pictureId));
//    }

//    @Test
//    public void testGetPictureByUserId() throws Exception {
//        // Arrange
//        String userId = "user1";
//        User user = TestFixtures.adminUser;
//        user.setId(userId);
//
//        FileDto fileDto = TestFixtures.testFileDto;
//        fileDto.setId("1");
//
//        List<File> mockFiles = Collections.singletonList(File.builder().build());
//
//        when(userService.findByUserId(ArgumentMatchers.eq(userId))).thenReturn(user);
//        when(fileService.findPicturesByUser(ArgumentMatchers.eq(user))).thenReturn(mockFiles);
//        when(fileMapper.toDto(any(File.class))).thenReturn(fileDto);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.get("/pictures/user/{userId}", userId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//    }
//
//    @Test
//    public void testUpdatePicture() throws Exception {
//        // Arrange
//        String pictureId = "1";
//        FileDto updatedFileDto = TestFixtures.testFileDto;
//        updatedFileDto.setId(pictureId);
//
//        File updatedFile = TestFixtures.testFileTypeFile;
//        updatedFile.setId(pictureId);
//
//        when(fileService.updatePicture(ArgumentMatchers.eq(pictureId), any(File.class))).thenReturn(updatedFile);
//        when(fileMapper.toEntity(any(FileDto.class))).thenReturn(updatedFile);
//        when(fileMapper.toDto(any(File.class))).thenReturn(updatedFileDto);
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.patch("/pictures/{pictureId}", pictureId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedFileDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(pictureId));
//    }
//
//    @Test
//    public void testDeletePictureByPictureId() throws Exception {
//        // Arrange
//        String pictureId = "1";
//
//        // Act & Assert
//        mockMvc.perform(MockMvcRequestBuilders.delete("/pictures/{pictureId}", pictureId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        Mockito.verify(fileService, Mockito.times(1)).deletePictureByPictureId(ArgumentMatchers.eq(pictureId));
//    }
//}
