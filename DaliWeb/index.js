const UploadArt = document.querySelector('.UploadArt')
UploadArt.addEventListener('submit', (e) => {

  e.preventDefault();
  const artName = UploadArt.querySelector('.artName').value;
  const tag = UploadArt.querySelector('.tag').value;
  const lat = UploadArt.querySelector('.lat').value;
  const long = UploadArt.querySelector('.long').value;
  const artFile = UploadArt.querySelector('.artFile').files[0];

  var formData = new FormData();
  formData.append('file', artFile);
  formData.append('artid', 'test');
  formData.append('artistid', 'orel');
  formData.append('tags', tag);
  formData.append('xPosition', lat);
  formData.append('yPosition', long);

  post('http://dalicontroller-env.j54ernqdif.eu-west-1.elasticbeanstalk.com/storage/uploadFile',
   formData);
})

function post(path, data) {
  return window.fetch(path, {
    method: 'POST',
    body: data
  })
}
