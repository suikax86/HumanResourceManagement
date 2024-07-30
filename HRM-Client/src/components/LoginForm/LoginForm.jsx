import React from 'react'
import { useState } from 'react'
import './login-form.scss'

function LoginForm() {
  const [email,setEmail] = useState('')
  const [pass,setPass] = useState('')
  const handleChangeEmail = (email) => {
    setEmail(email)
  }
  const handleChangePass = (pass) => {
    setPass(pass)
  }
  return (
    <div className='Loginform_container'>
      <div className='Loginform_title'>Sign in to your account</div>
      <div className='Loginform_email'>
        <div className='Loginform_email_title'>Email</div>
        <input
            type="text"
            placeholder="youremail@gmail.com"
            id="email"
            onChange={handleChangeEmail}
           className='Loginform_email_input'
          />
      </div>
      <div className='Loginform_pass'>
        <div className='Loginform_pass_title'>Password</div>
        <input
            type="password"
            placeholder="Your password"
            id="password"
            onChange={handleChangeEmail}
           className='Loginform_pass_input'
          />
      </div>
      <div className='Loginform_remember_forget'>
       <div className='Loginform_remember'>
          <input type='checkbox'/>
          <div className='Loginform_remember_text'>Remember me</div>
       </div>
       <a href='#' className='Loginform_forget'>Forgot password ?</a>
      </div>
      <button className='Loginform_button'>Sign in</button>
    </div>
  )
}

export default LoginForm