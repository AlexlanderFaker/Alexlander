function loginApi(data) {
    return $axios({
      'url': '/user/login',
      'method': 'post',
      data
    })
  }
function loginOutApi() {
  return $axios({
    'url': '/user/loginout',
    'method': 'post',
  })
}
function sendMsgApi(data) {
    return $axios({
        'url': '/user/sendMsg',
        'method': 'post',
        data
    })
}

  