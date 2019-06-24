class PathTraversal():
    "This is an example class"
    a = 10
    def func(self):
        print('Hello Example')

def _get_upload_bucket():
    s3config = S3CFG + "s3cfg"
    for line in open(s3config):
        if line.startswith("bucket ="):
            return line.split()[2]
    assert False, "Could not parse s3cfg file."
