package cl.MyMGroup.rentify.data.repository

import cl.MyMGroup.rentify.data.dao.UsuarioDao
import cl.MyMGroup.rentify.data.entity.UsuarioEntity

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun insertarUsuario(usuario: UsuarioEntity){
        usuarioDao.insertarUsuario(usuario);
    }

    suspend fun getUsuario(email: String, password: String): UsuarioEntity? {
        return usuarioDao.getUsuario(email, password)
    }

}