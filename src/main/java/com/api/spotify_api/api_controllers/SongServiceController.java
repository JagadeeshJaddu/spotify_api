package com.api.spotify_api.api_controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.spotify.playlistmanager.dtos.AddSongRequestDTO;
import com.spotify.playlistmanager.dtos.EntityType;
import com.spotify.playlistmanager.dtos.FindSongsByAlbumRequestDTO;
import com.spotify.playlistmanager.dtos.FindSongsByAlbumResponseDTO;
import com.spotify.playlistmanager.dtos.FindSongsByArtistRequestDTO;
import com.spotify.playlistmanager.dtos.FindSongsByArtistResponseDTO;
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

    @RequestMapping(value = "/spotify/songs/artist" , method = RequestMethod.GET)
    public ResponseEntity<Object> finnulldSongsByArtist(@RequestBody FindSongsByArtistRequestDTO findSongsByArtistRequestDTO)
    {
        Long artistId = findSongsByArtistRequestDTO.getArtistId();
        FindSongsByArtistResponseDTO findSongsByArtistResponseDTO = new FindSongsByArtistResponseDTO();
        List<Song> songs;
        try{
            songs = songService.findSongsByArtist(artistId);
            findSongsByArtistResponseDTO.setSongs(songs);
            findSongsByArtistResponseDTO.setStatus("SUCCESS");
        }
        catch(Exception e){
            findSongsByArtistResponseDTO.setStatus("FAILURE");
            findSongsByArtistResponseDTO.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(findSongsByArtistResponseDTO,HttpStatus.OK);
    }

    @RequestMapping(value = "/spotify/songs/album" , method = RequestMethod.GET)
    public ResponseEntity<Object> findSongsByAlbum(@RequestBody FindSongsByAlbumRequestDTO findSongsByAlbumRequestDTO)
    {
        Long albumId = findSongsByAlbumRequestDTO.getAlbumId();
        List<Song> songs;
        FindSongsByAlbumResponseDTO findSongsByAlbumResponseDTO = new FindSongsByAlbumResponseDTO();
        findSongsByAlbumResponseDTO.setAlbumId(albumId);
        try{
            songs = songService.findSongsByAlbum(albumId);
            findSongsByAlbumResponseDTO.setSongs(songs);
            findSongsByAlbumResponseDTO.setStatus("SUCCESS");
        }
        catch(Exception e){
            findSongsByAlbumResponseDTO.setStatus("FAILURE");
            findSongsByAlbumResponseDTO.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(findSongsByAlbumResponseDTO,HttpStatus.OK);
    }

    @RequestMapping(value = "/spotify/songs/all" , method = RequestMethod.GET)
    public ResponseEntity<Object> findAllSongs()
    {
        List<Song> songs = songService.findAllSongs();
        return new ResponseEntity<>(songs,HttpStatus.OK);
    }
}
