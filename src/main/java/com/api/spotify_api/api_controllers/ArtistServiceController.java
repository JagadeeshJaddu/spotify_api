package com.api.spotify_api.api_controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.spotify.playlistmanager.dtos.AddArtistRequestDTO;
import com.spotify.playlistmanager.dtos.EntityType;
import com.spotify.playlistmanager.dtos.ResponseDTO;
import com.spotify.playlistmanager.dtos.ResponseType;
import com.spotify.playlistmanager.models.Artist;
import com.spotify.playlistmanager.services.ArtistService;

@RestController
public class ArtistServiceController {
    private ArtistService artistService;

    public ArtistServiceController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @RequestMapping(value = "/spotify/artist", method = RequestMethod.POST)
    public ResponseEntity<Object> addArtist(@RequestBody AddArtistRequestDTO addArtistRequestDTO) {
        String artistName = addArtistRequestDTO.getName();
        Artist artist;
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setEntityType(EntityType.Artist);
        responseDTO.setResponseType(ResponseType.Addition);
        try {
            artist = artistService.addArtist(artistName);
            responseDTO.setEntityId(artist.getId());
            responseDTO.setStatus("SUCCESS");
        } catch (Exception e) {
            responseDTO.setStatus("FAILURE");
            responseDTO.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
