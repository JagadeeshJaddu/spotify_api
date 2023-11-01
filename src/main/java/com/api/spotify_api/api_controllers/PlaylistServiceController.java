package com.api.spotify_api.api_controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.spotify.playlistmanager.dtos.CreatePlaylistRequestDTO;
import com.spotify.playlistmanager.dtos.EntityType;
import com.spotify.playlistmanager.dtos.RemovePlaylistRequestDTO;
import com.spotify.playlistmanager.dtos.ResponseDTO;
import com.spotify.playlistmanager.dtos.ResponseType;
import com.spotify.playlistmanager.dtos.SongToPlaylistRequestDTO;
import com.spotify.playlistmanager.models.Playlist;
import com.spotify.playlistmanager.services.PlaylistService;

@RestController
public class PlaylistServiceController {
    private PlaylistService playlistService;

    public PlaylistServiceController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @RequestMapping(value = "/spotify/playlist", method = RequestMethod.POST)
    public ResponseEntity<Object> createPlaylist(@RequestBody CreatePlaylistRequestDTO createPlaylistRequestDTO) {
        Playlist playlist;
        ResponseDTO responseDTO = new ResponseDTO();
        String name = createPlaylistRequestDTO.getName();
        responseDTO.setEntityType(EntityType.Playlist);
        responseDTO.setResponseType(ResponseType.Addition);
        try {
            playlist = playlistService.createPlaylist(name);
            responseDTO.setEntityId(playlist.getId());
            responseDTO.setStatus("SUCCESS");
        } catch (Exception e) {
            responseDTO.setStatus("FAILURE");
            responseDTO.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/spotify/playlist/song", method = RequestMethod.POST)
    public ResponseEntity<Object> addSongToPlaylist(@RequestBody SongToPlaylistRequestDTO songToPlaylistRequestDTO) {
        Playlist playlist;
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setEntityType(EntityType.Playlist);
        responseDTO.setResponseType(ResponseType.Addition);
        Long playlistId = songToPlaylistRequestDTO.getPlaylistId();
        Long songId = songToPlaylistRequestDTO.getSongId();
        try {
            playlist = playlistService.addSongToPlaylist(playlistId, songId);
            responseDTO.setEntityId(playlist.getId());
            responseDTO.setStatus("SUCCESS");
        } catch (Exception e) {
            responseDTO.setStatus("FAILURE");
            responseDTO.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/spotify/playlist/song", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeSongFromPlaylist(
            @RequestBody SongToPlaylistRequestDTO songToPlaylistRequestDTO) {
        Playlist playlist;
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setEntityType(EntityType.Playlist);
        responseDTO.setResponseType(ResponseType.Removal);
        Long playlistId = songToPlaylistRequestDTO.getPlaylistId();
        Long songId = songToPlaylistRequestDTO.getSongId();
        try {
            playlist = playlistService.removeSongFromPlaylist(playlistId, songId);
            responseDTO.setEntityId(playlist.getId());
            responseDTO.setStatus("SUCCESS");
        } catch (Exception e) {
            responseDTO.setStatus("FAILURE");
            responseDTO.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/spotify/playlist" , method = RequestMethod.DELETE)
    public ResponseEntity<Object> removePlaylist(@RequestBody RemovePlaylistRequestDTO removePlaylistRequestDTO) {
        Long playlistId = removePlaylistRequestDTO.getPlaylistId();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setEntityId(playlistId);
        responseDTO.setEntityType(EntityType.Playlist);
        responseDTO.setResponseType(ResponseType.Removal);
        try {
            playlistService.removePlaylist(playlistId);
            responseDTO.setStatus("SUCCESS");
        } catch (Exception e) {
            responseDTO.setStatus("FAILURE");
            responseDTO.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/spotify/playlist/all" , method = RequestMethod.GET)
    public ResponseEntity<Object> findAllPlaylists()
    {
        List<Playlist> playlists = playlistService.findAllPlaylists();
        return new ResponseEntity<>(playlists,HttpStatus.OK);
    }
}
