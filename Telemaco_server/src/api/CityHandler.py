from piston.handler import BaseHandler
from piston.utils import rc

from telemaco.models import City

class CityHandler(BaseHandler):
    allowed_methods = ('GET',)
    model = City
    
    def read(self, request, object_id=None):
        try:
            if object_id:
                return self.model.objects.get(pk=object_id)
            else:
                return rc.BAD_REQUEST
        except Exception:
            return rc.NOT_FOUND