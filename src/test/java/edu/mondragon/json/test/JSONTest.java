package edu.mondragon.json.test;

public class JSONTest {


	/*@Test
	public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
		String json = "{ \"name\": \"Baeldung\", \"java\": true }";
		JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
		 
		Assert.assertTrue(convertedObject.isJsonObject());
		Assert.assertTrue(convertedObject.get("name").getAsString().equals("Baeldung"));
		Assert.assertTrue(convertedObject.get("java").getAsBoolean() == true);
	}*/
	
	/*@Test
	public void testAdd() throws Exception {
	    HttpHeaders httpHeaders = Common.createAuthenticationHeaders("stephane" + ":" + "mypassword");
	    this.mockMvc.perform(
	        post("/admin/crud").headers(httpHeaders)
	        .param("firstname", "Stephane")
	        .param("lastname", "Eybert")
	        .param("login", "stephane")
	        .param("password", "toto")
	    ).andDo(print())
	    .andExpect(
	        status().isOk()
	    ).andReturn();
	}*/
	
	
}
