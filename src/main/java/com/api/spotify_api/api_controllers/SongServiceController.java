package com.api.spotify_api.api_controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.spotify.playlistmanager.dtos.AddSongRequestDTO;
import com.spotify.playlistmanager.dtos.EntityType;
import com.spotify.playlistmanager.dtos.ResponseDTO;
import com.spotify.playlistmanager.dtos.ResponseType;
import com.spotify.playlistmanager.models.Song;
import com.spotify.playlistmanager.services.SongService;

@RestController
public class SongServiceController {
    private SongService songService;

    public SongServiceController(SongService songService) {
        this.songService = songService;
    }

    @RequestMapping(value = "/spotify/songs", method = RequestMethod.POST)
    public ResponseEntity<Object> addSong(@RequestBody AddSongRequestDTO addSongRequestDTO) {
        Song song;
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setEntityType(EntityType.Song);
        responseDTO.setResponseType(ResponseType.Addition);
        String name = addSongRequestDTO.getName();
        Long artistId = addSongRequestDTO.getArtistId();
        int duration = addSongRequestDTO.getDuration();
        try {
            song = songService.addSong(name, artistId, duration);
            responseDTO.setEntityId(song.getId());
            responseDTO.setStatus("SUCCESS");
        } catch (Exception e) {
            responseDTO.setStatus("FAILURE");
            responseDTO.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
